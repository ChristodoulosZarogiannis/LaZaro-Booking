package property.gui;

import files.utilities.JImageFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * A panel where images can be viewed, added, or deleted
 */
public class ImageDisplayPanelGUI extends JPanel {
    /**
     * The images of this panel
     */
    private ArrayList<ImageIcon> imagesArray; // TODO Add array with full scale images
    /**
     * The index of the currently displayed image
     */
    private int imageIndex;

    /**
     * Buttons for moving to the next/previous image, adding and removing one
     */
    private JButton bNextImage, bPreviousImage, bAddImage, bRemoveImage;
    /**
     * The label that holds the image
     */
    private JLabel lImage;
    /**
     * A file chooser to pick and add new images
     */
    private JImageFileChooser fileChooser;
    /**
     * A tenth of the in-use screen's width, used to display the images in proportion to the screen
     */
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width / 10;

    /**
     * Creates a new panel with editable photos and an empty {@link ArrayList} of images
     */
    public ImageDisplayPanelGUI() {
        this(new ArrayList<>(), true);
    }
/*

    public ImageDisplayPanelGUI(boolean editablePhotos) {
        this(new ArrayList<>(), editablePhotos);
    }

    public ImageDisplayPanelGUI(ArrayList<ImageIcon> a) {
        this(a, true);
    }
*/

    /**
     * Creates a nwew panel with the given images stored in an {@link ArrayList} and whether images can be added or removed
     *
     * @param a              the images {@link ArrayList} to display
     * @param editablePhotos whether new images can be added or removed
     */
    public ImageDisplayPanelGUI(ArrayList<ImageIcon> a, boolean editablePhotos) {
        // init
        imageIndex = 0;
        bNextImage = new JButton(">");
        bPreviousImage = new JButton("<");
        lImage = new JLabel();
        imagesArray = new ArrayList<>();

        if (editablePhotos)
            this.setLayout(new GridLayout(2, 1, 0, 10));
        else
            this.setLayout(new GridLayout(1, 1, 0, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
//        this.setLayout(new BorderLayout());
        //init file chooser
        fileChooser = new JImageFileChooser("/home/lazaros/IdeaProjects/mybooking-lazaro/assets");
        fileChooser.setDialogTitle("Choose photos");
//        setLayout(new GridLayout(1, 3, 10, 10));
        bNextImage.addActionListener(l -> nextImage());
        bPreviousImage.addActionListener(l -> previousImage());
        bAddImage = new JButton("Add image(s)");
//        imageDisplayPanel = new ImageDisplayPanelGUI();
        bRemoveImage = new JButton("Remove image");
        topPanel.add(bPreviousImage, BorderLayout.WEST);
        topPanel.add(lImage, BorderLayout.CENTER);
        topPanel.add(bNextImage, BorderLayout.EAST);
        bAddImage.addActionListener(e -> addImage());
        bRemoveImage.addActionListener(e -> removeImage());
        add(topPanel);
        if (!a.isEmpty()) { // if a is not empty
            imagesArray.addAll(a); // copy array
            ImageIcon disp = imagesArray.get(0);
//            disp.setImage(disp.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
            lImage.setIcon(getResizedImage(disp));
        }
        if (!editablePhotos) {
            bAddImage.setEnabled(false);
            bRemoveImage.setEnabled(false);
        } else {
            bottomPanel.add(bRemoveImage);
            bottomPanel.add(bAddImage);
            add(bottomPanel);
        }

    }

    /**
     * Move to the next image
     */
    public void nextImage() {
        if (imagesArray.isEmpty())
            return;
        if (imageIndex + 1 >= imagesArray.size()) {
            return;
        }
        imageIndex++;
        ImageIcon imageIcon = imagesArray.get(imageIndex);
//        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
        lImage.setIcon(getResizedImage(imageIcon));

    }

    /**
     * Go to the previous image
     */
    public void previousImage() {
        if (imagesArray.isEmpty())
            return;
        if (imageIndex - 1 < 0) { // check for out of bounds
            return;
        }
        imageIndex--;
        ImageIcon imageIcon = imagesArray.get(imageIndex);
//        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
        lImage.setIcon(getResizedImage(imageIcon));
    }

    /**
     * Update the {@link ArrayList} of {@link ImageIcon}s
     *
     * @param a The new images
     */
    public void updateArray(ArrayList<ImageIcon> a) {
        imagesArray.clear();
        imagesArray.addAll(a);
        imageIndex = 0;
        if (!a.isEmpty()) {
            // resize accordingly
            ImageIcon image = a.get(imageIndex);
            lImage.setIcon(getResizedImage(image));
        } else {
            lImage.setIcon(null);
        }
    }

    /**
     * Spawns a File Chooser to add images
     */
    public void addImage() {
        int retVal = fileChooser.showDialog(this, "Add photos");
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File f : files) {
                ImageIcon imageIcon = new ImageIcon(f.getPath());
                /*if (scale)
//                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(lImage.getWidth(), lImage.getHeight() * (imageIcon.getIconWidth() / imageIcon.getIconHeight()), Image.SCALE_DEFAULT));
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(lImage.getWidth(), -1, Image.SCALE_DEFAULT)); // -1 keeps aspect ratio, based on given width
                */
                imagesArray.add(imageIcon);
                lImage.setIcon(getResizedImage(imageIcon));
            }
        }

    }

    /**
     * Remove current image, based on the imageIndex
     */
    public void removeImage() {
        if (imagesArray.isEmpty()) {
            lImage.setIcon(null);
            imageIndex = 0;
        } else if (imagesArray.size() == 1) {
            imagesArray.clear();
//            fullScaleImagesArray.clear();
            imageIndex = 0;
            lImage.setIcon(null);
        } else if (imageIndex == imagesArray.size() - 1) {
            imagesArray.remove(imageIndex);
//            fullScaleImagesArray.remove(imageIndex);
            imageIndex--;
            lImage.setIcon(getResizedImage(imagesArray.get(imageIndex)));
        } else {
            imagesArray.remove(imageIndex);
//            fullScaleImagesArray.remove(imageIndex);
            lImage.setIcon(getResizedImage(imagesArray.get(imageIndex)));
        }
    }
/*

    public int getImageIndex() {
        return imageIndex;
    }
*/

    /**
     * Get the images {@link ArrayList}
     *
     * @return an {@link ArrayList} containing all the images of this panel
     */
    public ArrayList<ImageIcon> getImagesArray() {
        return imagesArray;
    }

    /**
     * Resizes an image and returns it, based on screen width
     *
     * @param image the image to resize
     * @return the resized image
     */
    private ImageIcon getResizedImage(ImageIcon image) {
        image.setImage(image.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
        return image;
    }
}
