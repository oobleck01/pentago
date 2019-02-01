package core.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import core.entities.Camera;
import core.entities.Entity;

public class Animation {

	private List<Vector3f[]> animation;
	private List<AnimationEntity> entities;
	private int duration;
	private int currentFrame;
	
	public Animation(int duration) {
		this.duration = duration;
		currentFrame = 0;
		animation = new ArrayList<Vector3f[]>();
		entities = new ArrayList<AnimationEntity>();
	}
	
	public AnimationEntity addEntity(Entity e) {
		AnimationEntity aEntity = new AnimationEntity(e, null);
		entities.add(aEntity);
		animation.add(new Vector3f[duration]);
		return aEntity;
	}
	
	public AnimationEntity addCamera(Camera c) {
		AnimationEntity aEntity = new AnimationEntity(null, c);
		entities.add(aEntity);
		animation.add(new Vector3f[duration]);
		return aEntity;
	}
	
	public void addKeyframe(int frame, Vector3f position, AnimationEntity object) {
		int index = entities.indexOf(object);
		animation.get(index)[frame] = position;
	}
	
	public Map<AnimationEntity, Vector3f> giveFrame(){
		 Map<AnimationEntity, Vector3f> ret = new  HashMap<AnimationEntity, Vector3f>();
		 int i = 0;
		 for(AnimationEntity ae: entities) {
			 ret.put(ae, animation.get(i)[currentFrame]);
			 i++;
		 }
		currentFrame++;
		 return ret;
	}
	
	public void finalize() {
		for(Vector3f[] v: animation) {
			
		}
	}
	
	
	public static void main(String[] args) {
		Animation test = new Animation(5);
		Entity e = new Entity(null, null, 0, 0, 0, 0);
		Camera c = new Camera(e);
		AnimationEntity ae = test.addEntity(e);
		AnimationEntity ac = test.addCamera(c);
		test.addKeyframe(0, new Vector3f(0,0,0), ae);
		test.addKeyframe(1, new Vector3f(0,1,0), ac);
		System.out.println(test);
	}
	
}
