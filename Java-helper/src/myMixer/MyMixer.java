package myMixer;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;

public class MyMixer {
	
	Mixer mixer;
	Clip clip;
	Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
	public static final int DEFAULT_MIXER = 1;
	
	public MyMixer(Mixer mixer) {
		//this.mixer = mixer;
		
	}
	/**
	 * Methods to return mixer | doesn't work :/ |
	 * @return mixer
	 */
	//@Deprecated
	public Mixer getMixer() {
		return this.mixer;
	}
	/**
	 * Sets constructed mixer
	 * @param info - System mixer info
	 */
	public void setMixer(Mixer.Info info) {
		this.mixer = AudioSystem.getMixer(info);
	}
	/**
	 * @return array of system mixers
	 */
	public Mixer.Info[] getMixerInfo(){
		return this.mixerInfo;	
	}
	/**
	 * @param n - system mixer index
	 * @return	mixer w/ specified index
	 */
	public Mixer.Info getMixerInfo(int n){
		int number = n-1;
		if(number<=mixerInfo.length || !(number<=0)) {
		return this.mixerInfo[number];}
		else {throw new NullPointerException();}
	}
	/**
	 * Tries to start clip with default system mixer
	 */
	public void tryToStartPlaying(String soundUrls) {
		setMixer(getMixerInfo(MyMixer.DEFAULT_MIXER));
		DataLine.Info dlInfo = new DataLine.Info(Clip.class, null);
		try {
			this.clip = (Clip)this.mixer.getLine(dlInfo);
		} catch (Exception e) {System.err.println("NOPE " + e);}
		
		try {
			URL soundUrl = new URL(soundUrls);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
			this.clip.open(ais);
			this.clip.start();
			
		} catch (Exception e) {System.err.println("NOPE "); e.printStackTrace();}
	}
	
	public void tryToStartPlaying(URL soundUrl) {
		setMixer(getMixerInfo(MyMixer.DEFAULT_MIXER));
		DataLine.Info dlInfo = new DataLine.Info(Clip.class, null);
		try {
			this.clip = (Clip)this.mixer.getLine(dlInfo);
		} catch (Exception e) {System.err.println("NOPE " + e);}
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
			this.clip.open(ais);
			this.clip.start();
			
		} catch (Exception e) {System.err.println("NOPE "); e.printStackTrace();}
	}
	
	public void Looped(boolean is) {
		if(is==true) {
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else if(is==false) {
		this.clip.loop(0);}
	}
	
	public void setLoop(int count) {
		
		this.clip.loop(count);
	}
	
	public void tryToClose() throws NullPointerException {
		clip.close();
		clip.stop();
	}
}