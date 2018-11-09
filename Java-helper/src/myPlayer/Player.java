package myPlayer;

import java.awt.Canvas;
import java.net.URL;
import javax.swing.JFrame;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Player {
	MediaPlayerFactory mpf;
	EmbeddedMediaPlayer emp;
	JFrame f;
	static boolean playing;
	static boolean paused;
	private static final String skele_geometry = "85:74";

	public Player(JFrame f, Canvas c) {
		this.f = f;
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"C:\\Program Files\\VideoLAN\\VLC\\");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
		mpf = new MediaPlayerFactory();
		emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
		emp.setVideoSurface(mpf.newVideoSurface(c));
	}


	public void setFile(String file) {
		this.emp.prepareMedia(file);
	}

	public void setFile(URL file) {
		String noURL = String.valueOf(file);
		this.emp.prepareMedia(noURL);
	}

	public void play() {
		if(playing == false) {
			this.f.setVisible(true);
			this.emp.play();
			playing=true;
			paused=false;}
		else{return;}
	}

	public void stop() {
		if(playing==true) {
			this.emp.stop();
			paused=false;}
		else {return;}

	}
	@Deprecated
	public void hold() {
		if(playing==true) {
			this.emp.pause();
			paused=true;}
		else {return;}
	}

	public void cropGeometry(String geom){
		this.emp.setCropGeometry(geom);
	}

	public void loop(boolean loop) {
		emp.setRepeat(loop);
	}

	public void enableSound(boolean sound) {
		int vol;
		if(sound==false) vol = 0;
		else vol = 100;
		emp.setVolume(vol);
	}

	public void skeleTrim() {
		setFile("C:\\Users\\User\\Desktop\\watch this if youre sad.mp4");
		cropGeometry(skele_geometry);
		loop(true);
		enableSound(true);
	}

	public void printState() {
		System.out.println(this.emp.getMediaPlayerState());
	}
}
