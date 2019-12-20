package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture mainImage;
    static int levell = 0;
    BitmapFont FontRed1;


    public MainScreen(final Drop gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        mainImage = new Texture("1.png");
        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
       // Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(mainImage, 0, 0);
        FontRed1.draw(game.batch, "Level: "+ levell, 670, 240);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new MainScreen2(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose() {

    }
}