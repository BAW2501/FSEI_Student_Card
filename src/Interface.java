import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Interface extends JFrame {

    Student etd1 = null;
    final JLabel cart = new JLabel();
    final Locale arabic = Locale.of("ar");
    final JComboBox<String> wilaya_combobox = new JComboBox<>(Data.wilayasAr);
    final JComboBox<String> faculte_combobox = new JComboBox<>(Data.FacultyNames);
    JComboBox<String> filiere_combobox = new JComboBox<>(Data.commonBranchesAr[0]);
    final String[] annnes = new String[]{"2011/2012", "2012/2013", "2013/2014", "2014/2015", "2015/2016", "2016/2017", "2017/2018", "2018/2019", "2019/2020", "2020/2021"};
    final JComboBox<String> annee_etude_combobox = new JComboBox<>(annnes);
    final JPanel pan_formulaire = new JPanel();
    final JPanel pan_info_entree = new JPanel();
    final JPanel panel3 = new JPanel();
    final JPanel pan_filiere = new JPanel(new GridLayout(1, 1));
    final JPanel Menu_Principal = new JPanel();
    final JPanel pan_cart = new JPanel();
    final JTextField nom_fr_textfield = new JTextField();
    final JTextField nom_ar_textfield = new JTextField();
    final JTextField prenom_fr_textfield = new JTextField();
    final JTextField prenom_ar_textfield = new JTextField();
    private String path_photo = null;
    final UtilDateModel model = new UtilDateModel();
    final JPanel pan_listEtudiant = new JPanel();
    final JButton retour = new JButton("return");

    public Interface() {
        setFormulaire();
        setApercuCart();

        setMenuPrincipal();
        add(Menu_Principal);

        setTitle("Gestionnaire de carte d'étudiant");

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void setModifier() {
        Object[][] data;
        String[] columnNames = {"Numero", "Nom", "Prenom"};
        DefaultTableModel tableModel;
        JTable table;
        data = Main.bdd.convertToData();
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(380, 280));
        pan_listEtudiant.add(scrollPane);

        JButton modify = new JButton("Modifie");
        pan_listEtudiant.add(modify);
        modify.addActionListener(_ -> {
            if (table.getSelectedRow() > -1) {
                // print first column value from selected row
                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString() + " " + table.getSelectedRow());
                etd1 = Main.bdd.getList().get(table.getSelectedRow());

                Main.bdd.getList().remove(table.getSelectedRow());
                remove(pan_listEtudiant);
                nom_fr_textfield.setText(etd1.getFamilyName());
                nom_ar_textfield.setText(etd1.getFamilyNameAR());
                prenom_fr_textfield.setText(etd1.getFirstName());
                prenom_ar_textfield.setText(etd1.getFirstNameAR());
                for (String c : Data.wilayasAr) {
                    if (c.equals(etd1.getBirthPlace())) {
                        wilaya_combobox.setSelectedItem(c);
                    }
                }

                int i;
                for (i = 0; i < Data.FacultyNames.length; i++) {
                    if (Data.FacultyNames[i].equals(etd1.getFaculty())) {
                        faculte_combobox.setSelectedItem(Data.FacultyNames[i]);
                        filiere_combobox = new JComboBox<>(Data.commonBranchesAr[i]);
                    }
                    for (String c : Data.commonBranchesAr[i]) {
                        if (c.equals(etd1.getSpeciality())) {
                            filiere_combobox.setSelectedItem(c);
                        }
                    }
                    for (String c : annnes) {
                        if (c.equals(etd1.getAcademicYear())) {
                            annee_etude_combobox.setSelectedItem(c);
                        }
                    }

                    model.setDate(etd1.getBirthDay().getYear(), etd1.getBirthDay().getMonthValue(), etd1.getBirthDay().getDayOfMonth());
                    path_photo = "pp1.jpg";
                }
                add(pan_formulaire);
                setSize(690, 510);
            }
        });

        retour.addActionListener(_ -> {
            remove(pan_listEtudiant);
            add(Menu_Principal);
            setSize(445, 280);
        });
        pan_listEtudiant.add(retour);
        pack();

    }

    private void setApercuCart() {
        final JPanel pan_apercu = new JPanel();

        final JPanel pan_button = new JPanel();

        pan_cart.add(cart);
        JButton modifier = new JButton("Modifier");
        JButton sauvegarde = new JButton("Sauvagardé");
        JButton imprime = new JButton("Imprimé");
        JButton goto_menuPrincipal = new JButton("MenuPrincipal");
        JButton neauveu_etudiant = new JButton("Créer un autre étudiant");
        pan_button.add(modifier);
        pan_button.add(sauvegarde);
        pan_button.add(imprime);
        pan_button.add(goto_menuPrincipal);
        pan_button.add(neauveu_etudiant);
        pan_cart.add(pan_button);

        setSize(590, 350);
        setLocationRelativeTo(null);
        modifier.addActionListener(_ -> {
            remove(pan_cart);
            add(pan_formulaire);
            setSize(690, 510);

        });

        sauvegarde.addActionListener(_ -> {
            JFileChooser sauvefichier = new JFileChooser();
            sauvefichier.setDialogTitle("sauvegarde carte etudiant");
            sauvefichier.setFileFilter(new FileNameExtensionFilter("*.png", "png"));

            int userSelection = sauvefichier.showSaveDialog(pan_apercu);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = sauvefichier.getSelectedFile();

                File imageFile = new File(fileToSave.getAbsolutePath());
                try {
                    Boolean exists = imageFile.createNewFile();
                    System.out.print(exists);
                    BufferedImage cart = etd1.makeStudentCard();
                    BufferedImage clone = new BufferedImage(cart.getWidth(), cart.getHeight(), cart.getType());
                    Graphics2D g2d = clone.createGraphics();
                    g2d.drawImage(cart, 0, 0, null);
                    g2d.dispose();
                    ImageIO.write(clone, "png", imageFile);
                } catch (IOException _) {
                }

            }

        });
        imprime.addActionListener(_ -> {

            //try {
            Toolkit tkp = cart.getToolkit();
            PrintJob pjp = tkp.getPrintJob(new JFrame(), null, null);
            Graphics g = pjp.getGraphics();
            cart.print(g);
            g.dispose();
            pjp.end();
        });
        goto_menuPrincipal.addActionListener(_ -> {
            remove(pan_cart);
            add(Menu_Principal);
            setSize(445, 280);
        });
        neauveu_etudiant.addActionListener(_ -> {
            //setFormulaire();
            nom_ar_textfield.setText("");
            prenom_ar_textfield.setText("");
            nom_fr_textfield.setText("");
            prenom_fr_textfield.setText("");

            remove(pan_cart);
            add(pan_formulaire);
            setSize(690, 510);

        });
    }

    private void setFormulaire() {
        //setMenuPrincipal();
        pan_formulaire.setLayout(new BoxLayout(pan_formulaire, BoxLayout.PAGE_AXIS));
        setTitle("Gestionnaire de carte d'étudiant");

        ImageIcon logo = new ImageIcon(new ImageIcon("UMAB_LOGO.png").getImage().getScaledInstance(210, 204, Image.SCALE_SMOOTH));
        final JLabel lablogo = new JLabel(logo, SwingConstants.CENTER);
        lablogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //lablogo.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        pan_info_entree.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pan_formulaire.add(lablogo);

        final GridLayout layout = new GridLayout(0, 4);
        pan_info_entree.setLayout(layout);
        layout.setHgap(10);
        layout.setVgap(10);
        final JLabel nom_fr = new JLabel("Nom (en FR)");

        pan_info_entree.add(nom_fr);
        pan_info_entree.add(nom_fr_textfield);

        final JLabel nom_ar = new JLabel("Nom (en AR)");

        nom_ar_textfield.setComponentOrientation(ComponentOrientation.getOrientation(arabic));
        pan_info_entree.add(nom_ar);
        pan_info_entree.add(nom_ar_textfield);

        final JLabel prenom_fr = new JLabel("Prénom (en FR)");

        pan_info_entree.add(prenom_fr);
        pan_info_entree.add(prenom_fr_textfield);

        final JLabel prenom_ar = new JLabel("Prénom (en AR)");

        prenom_ar_textfield.setComponentOrientation(ComponentOrientation.getOrientation(arabic));
        pan_info_entree.add(prenom_ar);
        pan_info_entree.add(prenom_ar_textfield);

        final JLabel date_naissance = new JLabel("Date de naissance");

        model.setDate(2000, 0, 1);
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        pan_info_entree.add(date_naissance);
        pan_info_entree.add(datePicker);

        final JLabel lieu_naissance = new JLabel("Lieu de naissance");
        pan_info_entree.add(lieu_naissance);
        pan_info_entree.add(wilaya_combobox);
        final JLabel Faculte = new JLabel("Faculté");
        faculte_combobox.addActionListener(_ -> {
            pan_filiere.remove(filiere_combobox);
            filiere_combobox = new JComboBox<>(Data.commonBranchesAr[faculte_combobox.getSelectedIndex()]);
            pan_filiere.add(filiere_combobox);
            revalidate();
        });
        pan_info_entree.add(Faculte);
        pan_info_entree.add(faculte_combobox);

        final JLabel filiere = new JLabel("Filière");

        pan_filiere.add(filiere_combobox);

        pan_info_entree.add(filiere);
        pan_info_entree.add(pan_filiere);

        final JLabel Annee = new JLabel("Année d'étude");
        pan_info_entree.add(Annee);
        pan_info_entree.add(annee_etude_combobox);

        final JLabel pic = new JLabel("ID/Photo");
        JButton btnpic = new JButton("Choix photo");
        btnpic.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("user.home"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                path_photo = selectedFile.getAbsolutePath();
            }
        });

        pan_info_entree.add(pic);
        pan_info_entree.add(btnpic);

        JButton suivant = getjButton(datePicker);

        panel3.add(suivant);

        pan_formulaire.add(pan_info_entree);
        pan_formulaire.add(panel3);

        setSize(690, 510);
        setLocationRelativeTo(null);
    }

    private JButton getjButton(JDatePickerImpl datePicker) {
        JButton suivant = new JButton("Enregistrer");
        suivant.setBackground(Color.green);
        suivant.setForeground(Color.WHITE);
        suivant.addActionListener(_ -> {

            if (!nom_fr_textfield.getText().isEmpty() && !prenom_fr_textfield.getText().isEmpty() && !nom_ar_textfield.getText().isEmpty() && !prenom_ar_textfield.getText().isEmpty() && path_photo != null) {
                etd1 = new Student("18183703" + wilaya_combobox.getSelectedIndex() + faculte_combobox.getSelectedIndex() + annee_etude_combobox.getSelectedIndex() + Main.bdd.getList().size(), nom_fr_textfield.getText(), prenom_fr_textfield.getText(), nom_ar_textfield.getText(), prenom_ar_textfield.getText(), LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getDay()), Data.wilayasAr[wilaya_combobox.getSelectedIndex()], Data.commonBranchesAr[faculte_combobox.getSelectedIndex()][filiere_combobox.getSelectedIndex()], Data.FacultyNames[faculte_combobox.getSelectedIndex()], (String) annee_etude_combobox.getSelectedItem(), path_photo);
                Main.bdd.add(etd1);
                pan_listEtudiant.removeAll();

                for (Student temp : Main.bdd.getList()) {
                    System.out.print(temp + "\n");
                }
                remove(pan_formulaire);
                try {
                    cart.setIcon(new ImageIcon(etd1.makeStudentCard().getScaledInstance(420, 260, Image.SCALE_SMOOTH)));
                } catch (IOException ioException) {
                    System.out.print(ioException.toString());
                }

                add(pan_cart);

                setSize(590, 350);
            } else {
                JOptionPane.showMessageDialog(pan_formulaire, "Remplissez tout");
            }
        });
        return suivant;
    }

    private void setMenuPrincipal() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JPanel panel2 = new JPanel();

        ImageIcon icon = new ImageIcon(new ImageIcon("UMAB_LOGO.png").getImage().getScaledInstance(210, 204, Image.SCALE_SMOOTH));
        final JLabel lab = new JLabel(icon);
        lab.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 15));
        Menu_Principal.add(lab);
        JButton b1 = new JButton("Nouvel Etudiant");
        b1.setBackground(Color.WHITE);
        JButton b2 = new JButton("Modifier/info Etudiant");
        b2.setBackground(Color.WHITE);
        JButton b3 = new JButton("Liste des Etudiants");
        b3.setBackground(Color.WHITE);
        JButton b4 = new JButton("Supprimer un Etudiant");
        b4.setBackground(Color.RED);
        b4.setForeground(Color.WHITE);

        panel2.setLayout(new GridLayout(0, 1));

        panel2.add(b1);
        panel2.add(Box.createRigidArea(new Dimension(0, 30)));
        panel2.add(b2);
        panel2.add(Box.createRigidArea(new Dimension(0, 30)));
        panel2.add(b3);
        panel2.add(Box.createRigidArea(new Dimension(0, 30)));
        panel2.add(b4);
        b1.addActionListener(_ -> {
            remove(Menu_Principal);
            add(pan_formulaire);
            setSize(690, 510);
            setLocationRelativeTo(null);
        });
        b2.addActionListener(_ -> {
            remove(Menu_Principal);
            add(pan_listEtudiant);
            pan_listEtudiant.removeAll();
            setModifier();
            revalidate();
            repaint();
            pack();
            setLocationRelativeTo(null);
        });
        b3.addActionListener(_ -> {
            remove(Menu_Principal);

            add(pan_listEtudiant);

            pan_listEtudiant.removeAll();
            setModifier();
            revalidate();
            repaint();

            setLocationRelativeTo(null);
        });
        b4.addActionListener(_ -> JOptionPane.showConfirmDialog(Menu_Principal, "Encore à implementé", "info", JOptionPane.DEFAULT_OPTION));
        Menu_Principal.add(panel2);
        setSize(445, 280);
        setLocationRelativeTo(null);
    }

}
