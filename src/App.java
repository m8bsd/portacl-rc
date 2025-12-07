import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class App implements ApplicationListener
{
    Texture tex;
    SpriteBatch spr;

    @Override
    public void create()
    {
        System.out.println("start");

        tex = new Texture("libgdx.png");

        spr = new SpriteBatch();
    }

    @Override
    public void resize(int i, int i2)
    {

    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        spr.begin();

        spr.draw(tex, 100, 100);
        spr.end();
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
