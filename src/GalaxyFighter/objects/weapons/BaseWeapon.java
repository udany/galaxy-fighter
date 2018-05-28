package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.window.Game;

abstract public class BaseWeapon {
    abstract public void fire(Game game, BaseShip ship);
}