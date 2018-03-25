package kivaaz.com.signaturelibrary.Signature.Signature;

import android.graphics.Path;

/**
 * Created by Muguntan on 3/25/2018.
 */

public class FingerPath {
    public int color;
    public Path path;

    public FingerPath(int color, Path path) {
        this.color = color;
        this.path = path;
    }
}
