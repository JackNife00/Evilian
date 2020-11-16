package com.prog.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.prog.world.Livello;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Enemy extends Entity implements Poolable{
    protected float life;
    //public perche' dovra' essere accessibile dal level contact listener
    protected boolean alive;
    protected boolean walkLeft;
    protected float SPEED;
    
    public Enemy()
    {
        super();
    }
    
    public abstract void init(Vector2 loadingPosition, float hp);
    public void damage(float dmg)
    {
        life -= dmg;
        if(life <= 0)
        {
            //assegno 20 punti ogni volta che muore un nemico 
            Livello.addPoints(20);
            alive = false;
        }
    }

    public void salvStato(FileWriter wr) throws IOException
    {
        String toWrite=null;
        Rectangle r=getPos();
        /*posizione, vita, velocità, id*/
        if(this instanceof EnemyA)
            toWrite = "E " + r.x + " " + r.y + " " + life + "\n";
        else if(this instanceof EnemyB)
            toWrite = "E2 " + r.x + " " + r.y + " " + life + "\n";
        
        if(toWrite != null)
            wr.write(toWrite);
    }
    
    public void debuffVelocita()
    {
        this.SPEED = 0.2f;
    }
    
    public void setWalkLeft(boolean f)
    {
        walkLeft=f;
    }
    
    public boolean isAlive()
    {
        return alive;
    }
    
    public void setAlive(boolean f)
    {
        alive=f;
    }
}
