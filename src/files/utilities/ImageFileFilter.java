package files.utilities;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * This class is taken from the oracle documentation, and is used in the file chooser when adding images to select only image files
 *
 * @author Lazaros Gogos
 * @version 2021.12.31
 * @see <a href=https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html#customtask>This</a> for more info
 */
public class ImageFileFilter extends FileFilter {

    /**
     * Method which makes sure only folders or image files are accepted
     *
     * @param f each file the File Chooser is processing
     * @return whether to show it as available or not
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.tiff) ||
                    extension.equals(Utils.tif) ||
                    extension.equals(Utils.gif) ||
                    extension.equals(Utils.jpeg) ||
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.png)) {
                return true;
            }
        }

        return false;
    }

    /**
     * What this file filter is doing
     *
     * @return a string of what this file filter is doing
     */
    @Override
    public String getDescription() {
        return "Just images";
    }

    /**
     * Container class for image files
     *
     * @see <a href=https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html#customtask>This</a> for more info
     */
    private class Utils {
        public final static String jpeg = "jpeg";
        public final static String jpg = "jpg";
        public final static String gif = "gif";
        public final static String tiff = "tiff";
        public final static String tif = "tif";
        public final static String png = "png";

        /*
         * Get the extension of a file.
         */
        public static String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                ext = s.substring(i + 1).toLowerCase();
            }
            return ext;
        }
    }
}
