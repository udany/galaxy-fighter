package engine.input;

import engine.util.Event;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GenericInput {
    public class Button {
        public static final int A = 1;
        public static final int B = 2;
        public static final int X = 3;
        public static final int Y = 4;

        public static final int L = 5;
        public static final int R = 6;

        public static final int Start = 7;
        public static final int Select = 8;

        public static final int Up = 9;
        public static final int Down = 10;
        public static final int Left = 11;
        public static final int Right = 12;
    }

    public final UUID id = UUID.randomUUID();

    public final Event<Integer> onButtonDown = new Event<>();
    public final Event<Integer> onButtonUp = new Event<>();

    public GenericInput() {
        Keyboard kb = Keyboard.getInstance();

        kb.onKeyDown.addListener(code -> {
            if (keyboardAlias.containsKey(code)) {
                for (Integer btnCode : keyboardAlias.get(code)) {
                    onButtonDown.emit(btnCode);
                }
            }
        });
        kb.onKeyUp.addListener(code -> {
            if (keyboardAlias.containsKey(code)) {
                for (Integer btnCode : keyboardAlias.get(code)) {
                    onButtonUp.emit(btnCode);
                }
            }
        });
    }

    public GenericInput applyDefaultBinding() {
        // KB
        aliasKb(KeyEvent.VK_Z, Button.A);
        aliasKb(KeyEvent.VK_X, Button.B);
        aliasKb(KeyEvent.VK_C, Button.X);
        aliasKb(KeyEvent.VK_V, Button.Y);

        aliasKb(KeyEvent.VK_A, Button.L);
        aliasKb(KeyEvent.VK_S, Button.R);

        aliasKb(KeyEvent.VK_ENTER, Button.Start);
        aliasKb(KeyEvent.VK_BACK_SPACE, Button.Select);

        aliasKb(KeyEvent.VK_UP, Button.Up);
        aliasKb(KeyEvent.VK_DOWN, Button.Down);
        aliasKb(KeyEvent.VK_LEFT, Button.Left);
        aliasKb(KeyEvent.VK_RIGHT, Button.Right);

        // Controller
        aliasCtrl(Controller.Button.A, Button.A);
        aliasCtrl(Controller.Button.B, Button.B);
        aliasCtrl(Controller.Button.X, Button.X);
        aliasCtrl(Controller.Button.Y, Button.Y);

        aliasCtrl(Controller.Button.L, Button.L);
        aliasCtrl(Controller.Button.R, Button.R);

        aliasCtrl(Controller.Button.Start, Button.Start);
        aliasCtrl(Controller.Button.Select, Button.Select);

        aliasCtrl(Controller.Button.Up, Button.Up);
        aliasCtrl(Controller.Button.Down, Button.Down);
        aliasCtrl(Controller.Button.Left, Button.Left);
        aliasCtrl(Controller.Button.Right, Button.Right);

        aliasCtrl(Controller.Button.LeftStickU, Button.Up);
        aliasCtrl(Controller.Button.LeftStickD, Button.Down);
        aliasCtrl(Controller.Button.LeftStickL, Button.Left);
        aliasCtrl(Controller.Button.LeftStickR, Button.Right);

        return this;
    }

    Controller controller;
    public GenericInput(Controller c) {
        super();
        setController(c);
    }

    public void setController(Controller c) {
        if (controller != null) {
            removeController();
        }

        controller = c;

        controller.onButtonDown.addListener(code -> {
            if (controllerAlias.containsKey(code)) {
                for (Integer btnCode : controllerAlias.get(code)) {
                    onButtonDown.emit(btnCode);
                }
            }
        }, id.toString());

        controller.onButtonUp.addListener(code -> {
            if (controllerAlias.containsKey(code)) {
                for (Integer btnCode : controllerAlias.get(code)) {
                    onButtonUp.emit(btnCode);
                }
            }
        }, id.toString());
    }

    public void removeController() {
        controller.onButtonDown.removeListener(id.toString());
        controller.onButtonUp.removeListener(id.toString());
    }

    public GenericInput aliasKb(Integer key, Integer alias) {
        return aliasKb(key, Arrays.asList(alias));
    }
    public GenericInput aliasKb(Integer key, List<Integer> alias) {
        if (!keyboardAlias.containsKey(key)) {
            keyboardAlias.put(key, alias);
        } else {
            keyboardAlias.get(key).addAll(alias);
        }
        return this;
    }

    public GenericInput aliasCtrl(String button, Integer alias) {
        return aliasCtrl(button, Arrays.asList(alias));
    }
    public GenericInput aliasCtrl(String button, List<Integer> alias) {
        if (!controllerAlias.containsKey(button)) {
            controllerAlias.put(button, alias);
        } else {
            controllerAlias.get(button).addAll(alias);
        }
        return this;
    }

    HashMap<String, List<Integer>> controllerAlias = new HashMap<>();
    HashMap<Integer, List<Integer>> keyboardAlias = new HashMap<>();
}
