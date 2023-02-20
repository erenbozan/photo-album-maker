package frames;

import models.Album;
import models.Photo;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Window {

    private JFrame frame;
    private JTextField txtAlbumName;
    private JTextField txtDate;
    private JTextField txtAlbumLocation;
    private JTextField txtPhotoLocation;
    private JTextField txtAlbumTag1;
    private JTextField txtAlbumTag2;
    private JTextField txtAlbumTag3;
    private JTextField txtAlbumTag4;
    private JTextField txtAlbumTag5;
    private JTextField txtPhotoTag1;
    private JTextField txtPhotoTag2;
    private JTextField txtPhotoTag3;
    private JTextField txtPhotoTag4;
    private JTextField txtPhotoTag5;
    private Album selectedAlbum;
    private Map<String, Album> albums = new HashMap<>();
    private Map<String, Photo> photos = new HashMap<>();
    private static final String NOT_CHOSEN = "Seçilmedi";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Window window = new Window();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Construct the application.
     */
    public Window() {
        createAlbumDashboard();
    }

    private void createAlbumDashboard() {

        String albumPath = System.getProperty("user.dir") + "/albums/";

        frame = new JFrame();

        frame.setBounds(100, 100, 650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblAlbumCreationTitle = new JLabel("Albüm Yaratma Ekranı");
        lblAlbumCreationTitle.setBounds(50, 5, 300, 30);
        lblAlbumCreationTitle.setFont(new Font("Courier", Font.BOLD, 12));
        frame.getContentPane().add(lblAlbumCreationTitle);

        JLabel lblAlbumDate = new JLabel("Tarih Belirt : ");
        lblAlbumDate.setBounds(10, 32, 95, 21);
        frame.getContentPane().add(lblAlbumDate);

        txtDate = new JTextField();
        txtDate.setText("07-11-2012");
        txtDate.setBounds(130, 33, 125, 20);
        frame.getContentPane().add(txtDate);
        txtDate.setColumns(10);

        JLabel lblAlbumName = new JLabel("Albüm Adı : ");
        lblAlbumName.setBounds(10, 64, 95, 20);
        frame.getContentPane().add(lblAlbumName);

        txtAlbumName = new JTextField();
        txtAlbumName.setBounds(130, 64, 125, 20);
        frame.getContentPane().add(txtAlbumName);
        txtAlbumName.setColumns(10);

        frame.getContentPane().add(createTagLabel("Albüm Etiket 1 :", 10, 95));
        frame.getContentPane().add(createTagLabel("Albüm Etiket 2 :", 10, 120));
        frame.getContentPane().add(createTagLabel("Albüm Etiket 3 :", 10, 145));
        frame.getContentPane().add(createTagLabel("Albüm Etiket 4 :", 10, 170));
        frame.getContentPane().add(createTagLabel("Albüm Etiket 5 :", 10, 195));

        txtAlbumTag1 = createTagTextField(130, 95);
        frame.getContentPane().add(txtAlbumTag1);

        txtAlbumTag2 = createTagTextField(130, 120);
        frame.getContentPane().add(txtAlbumTag2);

        txtAlbumTag3 = createTagTextField(130, 145);
        frame.getContentPane().add(txtAlbumTag3);

        txtAlbumTag4 = createTagTextField(130, 170);
        frame.getContentPane().add(txtAlbumTag4);

        txtAlbumTag5 = createTagTextField(130, 195);
        frame.getContentPane().add(txtAlbumTag5);

        JLabel lblAlbumLocation = new JLabel("Albüm Konumu : ");
        lblAlbumLocation.setBounds(10, 220, 110, 15);
        frame.getContentPane().add(lblAlbumLocation);

        JLabel lblPhotoCreationTitle = new JLabel("Fotoğraf Ekleme Ekranı");
        lblPhotoCreationTitle.setBounds(330, 5, 300, 30);
        lblPhotoCreationTitle.setFont(new Font("Courier", Font.BOLD, 12));
        frame.getContentPane().add(lblPhotoCreationTitle);

        JLabel isChosenLabel = new JLabel(NOT_CHOSEN);
        JList albumList = createAlbumList(albumPath, isChosenLabel);
        JButton createAlbum = createProduceAlbumBtn(albumPath, albumList);
        frame.getContentPane().add(createAlbum);

        txtAlbumLocation = new JTextField();
        txtAlbumLocation.setBounds(130, 220, 125, 20);
        frame.getContentPane().add(txtAlbumLocation);
        txtAlbumLocation.setColumns(10);

        frame.getContentPane().add(albumList);

        JLabel lblSelectedAlbum = new JLabel("Seçilen Albüm : ");
        lblSelectedAlbum.setBounds(300, 190, 103, 18);
        frame.getContentPane().add(lblSelectedAlbum);

        isChosenLabel.setBounds(400, 190, 130, 20);
        frame.getContentPane().add(isChosenLabel);

        frame.getContentPane().add(createTagLabel("Fotoğraf Etiket 1 :", 300, 225));
        frame.getContentPane().add(createTagLabel("Fotoğraf Etiket 2 :", 300, 250));
        frame.getContentPane().add(createTagLabel("Fotoğraf Etiket 3 :", 300, 275));
        frame.getContentPane().add(createTagLabel("Fotoğraf Etiket 4 :", 300, 300));
        frame.getContentPane().add(createTagLabel("Fotoğraf Etiket 5 :", 300, 325));

        txtPhotoTag1 = createTagTextField(460, 225);
        frame.getContentPane().add(txtPhotoTag1);

        txtPhotoTag2 = createTagTextField(460, 250);
        frame.getContentPane().add(txtPhotoTag2);

        txtPhotoTag3 = createTagTextField(460, 275);
        frame.getContentPane().add(txtPhotoTag3);

        txtPhotoTag4 = createTagTextField(460, 300);
        frame.getContentPane().add(txtPhotoTag4);

        txtPhotoTag5 = createTagTextField(460, 325);
        frame.getContentPane().add(txtPhotoTag5);

        JLabel lblPhotoLocation = new JLabel("Fotoğraf Konum : ");
        lblPhotoLocation.setBounds(300, 350, 125, 15);
        frame.getContentPane().add(lblPhotoLocation);

        txtPhotoLocation = new JTextField();
        txtPhotoLocation.setBounds(460, 350, 125, 20);
        frame.getContentPane().add(txtPhotoLocation);
        txtPhotoLocation.setColumns(10);

        JButton addPhotoToAlbumButton = createAddPhotoToAlbumButton(albumPath, isChosenLabel, albumList);
        frame.getContentPane().add(addPhotoToAlbumButton);
    }

    private JButton createProduceAlbumBtn(String albumPath, JList albumList) {
        JButton createAlbum = new JButton("Albüm oluştur");
        createAlbum.setBounds(50, 250, 200, 23);

        createAlbum.addActionListener(e -> {

            Path newAlbumPath = Paths.get(albumPath + txtDate.getText() + "/" + txtAlbumName.getText());
            try {
                if (txtDate.getText().equals(""))
                    JOptionPane.showMessageDialog(frame, "Albüm tarihi boş olamaz");
                else if (txtAlbumName.getText().equals(""))
                    JOptionPane.showMessageDialog(frame, "Albüm adı boş olamaz");
                else {
                    if (!Files.isDirectory(newAlbumPath)) {
                        Files.createDirectories(newAlbumPath);
                        albumList.setModel(createDefaultListModel(albumPath));
                        albums.put("(" + txtDate.getText() + ") - " + txtAlbumName.getText(), new Album(txtDate.getText(), txtAlbumName.getText(), newAlbumPath.toString(), txtAlbumLocation.getText(),
                                txtAlbumTag1.getText(), txtAlbumTag2.getText(), txtAlbumTag3.getText(), txtAlbumTag4.getText(), txtAlbumTag5.getText()));
                        JOptionPane.showMessageDialog(frame, "Albüm başarıyla oluşturuldu.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Bu isimde bir albüm zaten kayıtlı.");
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Albüm oluşturulurken bir hata oluştu !!");
            }
        });
        return createAlbum;
    }

    private JButton createAddPhotoToAlbumButton(String albumPath, JLabel isChosenLabel, JList albumList) {
        JButton addPhotoBtn = new JButton("Seçilen albüme foto ekle");
        addPhotoBtn.setBounds(300, 400, 205, 23);
        addPhotoBtn.addActionListener(e -> {

            if (!isChosenLabel.getText().equals(NOT_CHOSEN)) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setDialogTitle("Foto Seçim Dialog");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (chooser.showOpenDialog(addPhotoBtn) == JFileChooser.APPROVE_OPTION) {
                    try {
                        String photoPath = albumPath + txtDate.getText() + "/" + txtAlbumName.getText();
                        FileUtils.copyToDirectory(new File(chooser.getSelectedFile().getAbsolutePath()), new File(photoPath));
                        photos.put(photoPath + "/" + chooser.getSelectedFile().getName(), new Photo(selectedAlbum, photoPath, txtPhotoLocation.getText(), txtPhotoTag1.getText(),
                                txtPhotoTag2.getText(), txtPhotoTag3.getText(), txtPhotoTag4.getText(), txtPhotoTag5.getText()));
                        albumList.clearSelection();
                        JOptionPane.showMessageDialog(frame, "foto başarıyla eklendi.");

                    } catch (IOException ea) {
                        ea.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Lütfen bir albüm seçiniz");
            }

        });
        return addPhotoBtn;
    }

    private JList createAlbumList(String albumPath, JLabel isChosenAlbumLabel) {
        JList list = new JList();
        list.setBounds(300, 32, 280, 150);
        list.setModel(createDefaultListModel(albumPath));
        list.addListSelectionListener(e1 -> {
            String selected = (String) list.getSelectedValue();
            isChosenAlbumLabel.setText(selected);
            selectedAlbum = albums.get(selected);
            if (selectedAlbum != null) {
                txtPhotoTag1.setText(selectedAlbum.getTag1());
                txtPhotoTag2.setText(selectedAlbum.getTag2());
                txtPhotoTag3.setText(selectedAlbum.getTag3());
                txtPhotoTag4.setText(selectedAlbum.getTag4());
                txtPhotoTag5.setText(selectedAlbum.getTag5());
                txtPhotoLocation.setText(selectedAlbum.getLocation());
            } else {
                txtPhotoTag1.setText("");
                txtPhotoTag2.setText("");
                txtPhotoTag3.setText("");
                txtPhotoTag4.setText("");
                txtPhotoTag5.setText("");
                txtPhotoLocation.setText("");
            }
        });
        return list;
    }


    private Set<String> listAlbumsUnderDate(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(File::isDirectory)
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    private DefaultListModel createDefaultListModel(String albumPath) {
        File directoryPath = new File(albumPath);
        DefaultListModel defaultListModel = new DefaultListModel();
        String albumDates[] = directoryPath.list();
        if (albumDates != null) {
            for (String content : albumDates) {
                Set<String> albumNames = listAlbumsUnderDate(albumPath + "/" + content);
                albumNames.forEach(albumName -> defaultListModel.addElement("(" + content + ") - " + albumName));
            }

        }
        return defaultListModel;
    }

    private JLabel createTagLabel(String name, int x, int y) {
        JLabel tagLabel = new JLabel(name);
        tagLabel.setBounds(x, y, 150, 15);
        return tagLabel;
    }

    private JTextField createTagTextField(int x, int y) {
        JTextField tag = new JTextField();
        tag.setBounds(x, y, 125, 20);
        tag.setColumns(10);
        return tag;
    }
}