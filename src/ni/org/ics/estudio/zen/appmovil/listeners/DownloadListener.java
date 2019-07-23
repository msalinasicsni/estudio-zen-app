package ni.org.ics.estudio.zen.appmovil.listeners;
public interface DownloadListener {
	void downloadComplete(String result);
	void progressUpdate(String message, int progress, int max);
}
