package testing;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import core.Constants;
import core.Scene;
import core.audio.AudioMaster;
import core.audio.Source;
import core.entities.Camera;
import core.entities.Entity;
import core.entities.Light;
import core.loaders.Loader;
import core.loaders.OBJFileLoader;
import core.models.RawModel;
import core.models.TexturedModel;
import core.renderEngine.DisplayManager;
import core.textures.ModelTexture;
import tools.Maths;

public class TestScene extends Scene{

	private float test = 0;
	
	public TestScene() {
		super();
	}
	
	@Override
	public void init() {
		Light sun = new Light(new Vector3f(0, 1000, -700), Maths.rgbToVector(255, 255, 255));
		super.lights.add(sun);
		
		ModelTexture texture = new ModelTexture(Loader.loadTexture("entities/dragonTexture"));
		texture.setReflectivity(0.1f);
		//texture.setFakeLighting(true);
		RawModel model = Loader.loadToVAO(OBJFileLoader.loadOBJ("entities/pentagoPanel"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity tmp = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 5f);
		AudioMaster.setDistanceAttenuationMethod(1, true);
		
		Source source = new Source();
		source.setLooping(true);
		source.play(AudioMaster.loadSound("songMono"));
		source.setGain(1f);
		source.setAttenuationVariables(2f, 25, 500);
		
		tmp.connectSource(source);
		
		super.camera = new Camera(tmp);
		
		super.entities.add(tmp);
		System.out.println("Done with init()");
	}
	
	
	@Override
	public void tickGame() {
		//System.out.println("Tick");
		super.camera.move();
		
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		Light.sort(super.lights, super.camera);
		super.render();
		
	}

	@Override
	public void tickMenu() {
		// TODO Auto-generated method stub
		
	}
	
	//Static methods
	
	public static void main(String[] args) {
		if (System.getProperty("org.lwjgl.librarypath") == null) {
		    Path path = Paths.get("libs/natives");
		    String librarypath = path.toAbsolutePath().toString();
		    System.setProperty("org.lwjgl.librarypath", librarypath);
		}
		
		//System.out.println(System.getProperty("java.library.path"));
		System.setProperty("org.lwjgl.util.Debug", "true");
		DisplayManager.createDisplay();
		Constants.state = Constants.STATE.GAME;
		TestScene scene = new TestScene();
		scene.start();
		DisplayManager.closeDisplay();
	}

}
