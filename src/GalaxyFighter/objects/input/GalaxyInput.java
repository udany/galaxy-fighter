package GalaxyFighter.objects.input;

import engine.input.Controller;
import engine.input.GenericInput;

public class GalaxyInput extends GenericInput {
    public GalaxyInput() {
        super();
        applyDefaultBinding();
    }

    public GalaxyInput(Controller c) {
        super(c);
        applyDefaultBinding();
    }
}
