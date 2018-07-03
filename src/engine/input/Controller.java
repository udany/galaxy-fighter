package engine.input;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import engine.util.Event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private Controller(int index) {
        this.index = index;

        thread = new Thread(() -> {
            while (true) {
                updateState();

                Thread.yield();
            }
        });

        thread.start();
    }

    int index;
    Thread thread;
    ControllerState state;

    private void updateState() {
        ControllerState currentState = manager.getState(index);

        if (state != null) {
            for (Button<Boolean> button : booleanButtons) {
                monitorBooleanState(button, currentState);
            }
            for (Button<Float> button : floatButtons) {
                monitorFloatState(button, currentState);
            }
        }

        state = currentState;
    }

    List<Button<Boolean>> booleanButtons = Arrays.asList(
            new Button<Boolean>(Button.A),
            new Button<Boolean>(Button.B),
            new Button<Boolean>(Button.X),
            new Button<Boolean>(Button.Y),

            new Button<Boolean>(Button.R),
            new Button<Boolean>(Button.L),

            new Button<Boolean>(Button.Start),
            new Button<Boolean>(Button.Select),

            new Button<Boolean>(Button.DPadUp, new String[]{Button.DPadUp, Button.Up}),
            new Button<Boolean>(Button.DPadDown, new String[]{Button.DPadDown, Button.Down}),
            new Button<Boolean>(Button.DPadLeft, new String[]{Button.DPadLeft, Button.Left}),
            new Button<Boolean>(Button.DPadRight, new String[]{Button.DPadRight, Button.Right})
    );

    List<Button<Float>> floatButtons = Arrays.asList(
            new Button<Float>(Button.LeftStickX, new String[]{Button.LeftStickL, Button.LeftStickR}),
            new Button<Float>(Button.LeftStickY, new String[]{Button.LeftStickD, Button.LeftStickU})
    );

    public final Event<String> onButtonDown = new Event<>();
    public final Event<String> onButtonUp = new Event<>();

    private void monitorBooleanState(Button<Boolean> btn, ControllerState currentState) {
        if (btn.get(state).booleanValue() != btn.get(currentState).booleanValue()) {
            Event<String> e = btn.get(state).booleanValue() ? onButtonUp : onButtonDown;

            for (String code : btn.fireCodes) {
                e.emit(code);
            }
        }
    }

    Float threshold = 0.3F;
    private int getButtonFloatState(float v) {
        return v > threshold ? 1 : (v < -threshold ? -1 : 0);
    }

    private void monitorFloatState(Button<Float> btn, ControllerState currentState) {
        int old = getButtonFloatState(btn.get(state));
        int cur = getButtonFloatState(btn.get(currentState));

        if (old != cur) {
            if (old != 0) {
                onButtonUp.emit(btn.fireCodes[old < 0 ? 0 : 1]);
            }
            if (cur != 0) {
                onButtonDown.emit(btn.fireCodes[cur < 0 ? 0 : 1]);
            }
        }
    }


    public class Button<T> {
        public static final String A = "a";
        public static final String B = "b";
        public static final String X = "x";
        public static final String Y = "y";

        public static final String L = "lb";
        public static final String R = "rb";

        public static final String Start = "start";
        public static final String Select = "back";

        public static final String Up = "up";
        public static final String Down = "down";
        public static final String Left = "left";
        public static final String Right = "right";

        public static final String DPadUp = "dpadUp";
        public static final String DPadDown = "dpadDown";
        public static final String DPadLeft = "dpadLeft";
        public static final String DPadRight = "dpadRight";

        private static final String LeftStickX = "leftStickX";
        private static final String LeftStickY = "leftStickY";

        public static final String LeftStickL = "leftStickXL";
        public static final String LeftStickR = "leftStickYR";
        public static final String LeftStickU = "leftStickXU";
        public static final String LeftStickD = "leftStickYD";


        private Field field;
        public String[] fireCodes;
        private Button(String fieldName) {
            this(fieldName, new String[]{fieldName});
        }
        private Button(String fieldName, String[] fireCodes) {
            try {
                field = ControllerState.class.getDeclaredField(fieldName);
                this.fireCodes = fireCodes;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        private T get(ControllerState state) {
            try {
                return (T) field.get(state);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * STATIC
     */
    public static final ArrayList<Controller> controllers = new ArrayList<>();
    public static final ControllerManager manager = new ControllerManager();
    public static void setup() {
        manager.initSDLGamepad();
    }

    public static int getCount() {
        return manager.getNumControllers();
    }

    public static Controller getController(int index) {
        if(index < controllers.size() && controllers.get(index) == null) {
            return controllers.get(index);
        } else if (index <= getCount()) {
            Controller c = new Controller(index);
            while (controllers.size() < index+1) controllers.add(null);
            controllers.set(index, c);
            return c;
        }

        return null;
    }
}
