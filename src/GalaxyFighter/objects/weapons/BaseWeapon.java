package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.main.Stage;
import engine.window.Game;

abstract public class BaseWeapon {
    public double cadence = .2;

    abstract public void fire(Stage game, BaseShip ship);
}