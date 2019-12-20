package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    final Drop game ;
    OrthographicCamera camera;
    Texture dropImage;
    Texture bucketImage;
    Texture mainImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;
    static int dropsGatchered;
    static int failed ;
    static int level = 0;


    public GameScreen (final Drop gam) {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();

        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket.png");
        mainImage = new Texture("5.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;
        GameScreen.level = 0;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render (float delta) {
        //Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(mainImage, 0, 0);
        game.font.draw(game.batch, "Drops Collected:  " + dropsGatchered, 40, 460);
        game.font.draw(game.batch, "Drops Failed:  " + failed, 40, 440);
        game.font.draw(game.batch, "Your Level:  " + level, 40, 420);
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop: raindrops){
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x -64 / 2);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {

            if (dropsGatchered < 41) {
                Rectangle raindrop = iter.next();
                int speed = 160;
                if ((dropsGatchered >= 5)&&(dropsGatchered < 15)) {
                    speed = 220;
                    level = 1;
                }
                if ((dropsGatchered >= 15)&&(dropsGatchered < 25)) {
                    speed = 260;
                    level = 2;
                }
                if ((dropsGatchered >= 25)&&(dropsGatchered < 31)) {
                    speed = 280;
                    level = 3;
                }
                if ((dropsGatchered >= 31)&&(dropsGatchered < 35)) {
                    speed = 380;
                    level = 4;
                }
                if ((dropsGatchered >= 35)&&(dropsGatchered < 41)) {
                    speed = 500;
                    level = 5;
                }
                raindrop.y -= speed * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
                failed++;
            }
            if (raindrop.overlaps(bucket)) {
                dropsGatchered++;
                dropSound.play();
                iter.remove();
            }
          }
        }
        if (dropsGatchered == 40) {
            game.setScreen(new GameOver(game));
            dispose();
        }
        if (failed>=6){
            game.setScreen(new GameOver2(game));
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public void show() {
        rainMusic.play();
    }
}