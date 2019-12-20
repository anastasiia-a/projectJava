package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameOver2 implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture mainImage;


    public GameOver2(final Drop gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        mainImage = new Texture("2.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(mainImage, 0, 0);
        game.font.draw(game.batch, "Your Level:  " + GameScreen.level, 363, 100);
        game.batch.end();


        if (Gdx.input.isTouched()){
            if  (MainScreen.levell < GameScreen.level) {
                MainScreen.levell = GameScreen.level;
                
            }
            //GameScreen.level = 0;
            GameScreen.dropsGatchered = 0;
            GameScreen.failed = 0;
            Gdx.app.exit();
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