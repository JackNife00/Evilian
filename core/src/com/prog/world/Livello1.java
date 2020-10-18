package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.prog.collision.LevelContactListener;
import com.prog.entity.Entity;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;

public class Livello1 extends Livello implements Screen{
    Player p;
    float delta;
    LevelContactListener lcl;
    
    //test UI
    Texture tex;
    Texture tex2;
    
    public Livello1(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight, Evilian game)
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,uiWidth,uiHeight,game);
        lcl=new LevelContactListener();
        world.setContactListener(lcl);

        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(world,map.getLayers().get("Collision_layer").getObjects());
        
        //ho bisgno di passare il listener come parametro per avere il flag inAir
        p=new Player(lcl, mouse);
        
        entities.add(p);
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravit� applicata essendo un body
        world.destroyBody(mouse.body);
        
        MANAGER_MUSIC.selectMusic(2);
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        cam.zoom-=0.5;
        
        //test UI
        tex=new Texture("images/ui/bg.png");
        tex2=new Texture("images/ui/fireball_1.png");
        
        //cam.translate(0f,-1f);
        level_ui.add(new UIElement(0,0,800,75,"images/ui/bg.png"),UI.ElementType.BACKGROUND);
        level_ui.add(new UIElement(0,0,64,64,"images/ui/fireball_2.png"),UI.ElementType.FOREGROUND);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        
        cam.position.set(Math.max(p.pos.x+0.5f,2f), Math.max(p.pos.y+0.2f,1.4f),0f);
        cam.update();
        level_ui.update();
        batch.setProjectionMatrix(cam.combined);
        
        //handleinput entita'
        for(Entity e:entities)
            e.handleInput();
        
        
        //handleinput del livello
        handleInput();
        
        //guardo entities e faccio gli update
        for(Entity e:entities)
            e.update(f);
        
        //draw
        draw();
        level_ui.draw();
        
        world.step(1/60f,6,2);
    }

    @Override
    public void resize(int width, int height) {
        //da sistemare il discorso del resize(molto confusionario con le viewport)
        mvfx.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
    }
    
    public void draw()
    {   
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0,75,800, 525);
        //prima renderizzo la mappa e poi il player o altre cose
        mapRenderer.setView(cam);
        mapRenderer.render();
        //guardo entities e renderizzo cose
        batch.begin();
        for(Entity e:entities)
            e.draw();
        batch.end();
        debug.render(world, cam.combined);
    }

}
