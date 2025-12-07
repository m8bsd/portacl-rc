import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.File;

public class Main
{
    public static void main(String[] args) {

        LwjglApplicationConfiguration.disableAudio = true;

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "App";
        cfg.width = 640;
        cfg.height = 480;

        File f = new File("lib/libgdx64.so");
        System.load(f.getAbsolutePath());

        new LwjglApplication(new App(), cfg);
    }
}
