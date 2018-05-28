package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.window.Game;

abstract public class BaseWeapon {
    public double cadence = .2;

    abstract public void fire(Game game, BaseShip ship);
}