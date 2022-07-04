package files.utilities;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * A file chooser that allows only for image files to be selected
 */
public class JImageFileChooser extends JFileChooser {
    /**
     * Constructor of a file chooser that allows only for image files to be selected
     *
     * @param path the starting path of the file chooser
     */
    public JImageFileChooser(String path) {
        super(path);
//        this.setLocale(Locale.ENGLISH);
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("");
        FileFilter filter = new ImageFileFilter();
        addChoosableFileFilter(filter);
        setFileFilter(filter);
        setMultiSelectionEnabled(true);
    }

}
