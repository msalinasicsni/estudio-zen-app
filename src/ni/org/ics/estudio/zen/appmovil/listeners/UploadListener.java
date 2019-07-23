package ni.org.ics.estudio.zen.appmovil.listeners;

public interface UploadListener {
    void uploadComplete(String result);
    void progressUpdate(String message, int progress, int max);
}
