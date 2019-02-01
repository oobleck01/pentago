package core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import core.audio.AudioMaster;
import core.controls.MousePicker;
import core.entities.Camera;
import core.entities.Entity;
import core.entities.Light;
import core.loaders.Loader;
import core.renderEngine.DisplayManager;
import core.renderEngine.MasterRenderer;

public abstract class Scene {
	
	protected List<Entity> entities;
	protected List<Light> lights;
	protected Camera camera;
	protected MousePicker picker;
	protected MasterRenderer renderer;
	
	public Scene() {
		AudioMaster.init();
		entities = new ArrayList<Entity>();
		lights = new ArrayList<Light>();
		renderer = new MasterRenderer(camera);
	}
	
	public abstract void init();
	
	public abstract void tickGame();
	
	public abstract void tickMenu();
	
	public void start() {
		init();
		while(!Display.isCloseRequested()) {
			if(Constants.state == Constants.STATE.MENU) {
				tickMenu();
			} else if(Constants.state == Constants.STATE.GAME) {
				tickGame();
			}
		}
		cleanUpEverything();
	}
	
	protected void render() {
		renderer.renderScene(entities, lights, camera, null);
		DisplayManager.updateDisplay();
	}
	
	public void cleanUpEverything() {
		for(Entity entity: entities) {
			if(entity.getSource() != null) {
				entity.getSource().delete();
			}
		}
		AudioMaster.cleanUp();
		renderer.cleanUp();
		entities.clear();
		lights.clear();
		Loader.cleanUp();
	}
	
}
