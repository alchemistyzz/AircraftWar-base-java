package edu.hitsz.aircraft;

public interface EnemyFactory {
    public abstract AbstractAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp);
}
