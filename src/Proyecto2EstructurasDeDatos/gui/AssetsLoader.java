package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.utils.AssetsManager;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * @author sebas
 */
enum AssetType {
    Image
}

/**
 * @author sebas
 */
class AssetInfo {
    String path;
    String name;
    AssetType type;

    AssetInfo(String path, String name, AssetType type) {
        this.path = path;
        this.name = name;
        this.type = type;
    }
}

/**
 * Clase que carga los distintos recursos usados por el programa
 *
 * @author sebas
 */
public class AssetsLoader {

    /**
     * Prepara un modal que bloquea todos los hilos de ejecucion cuando se hace
     * visible,
     * por lo que se ejecuta un hilo de Swing donde controlara la ui de carga, la
     * carga
     * de los distintos recursos y matar el modal para que la ejecucion del programa
     * vuela
     */
    public AssetsLoader() {
        var dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(320, 180);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setUndecorated(true);

        var assetLabel = new JLabel();
        var bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(true);

        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(bar, BorderLayout.CENTER);
        panel.add(assetLabel, BorderLayout.SOUTH);

        dialog.add(panel);

        var sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                String userDirectory = Paths.get("")
                        .toAbsolutePath()
                        .toString();
                System.out.println(userDirectory);
                var assetsLoadInfo = new AssetInfo[]{
                        new AssetInfo("./src/Proyecto2EstructurasDeDatos/assets/cat-kiss.gif", "cat-kiss", AssetType.Image),
                        new AssetInfo("./src/Proyecto2EstructurasDeDatos/assets/Amazon_icon.png", "amazon-icon",
                                AssetType.Image),
                        new AssetInfo("./src/Proyecto2EstructurasDeDatos/assets/left-arrow.png", "left-arrow",
                                AssetType.Image),
                        new AssetInfo("./src/Proyecto2EstructurasDeDatos/assets/cat-dance.gif", "cat-dance",
                                AssetType.Image),
                };

                int i = 0;
                for (var assetInfo : assetsLoadInfo) {
                    assetLabel.setText(assetInfo.path.substring("./src/proyecto2estructurasdedatos/".length()));
                    float porc = ((float) (assetsLoadInfo.length - (assetsLoadInfo.length - i)) / assetsLoadInfo.length)
                            * 100;
                    bar.setValue((int) porc);

                    switch (assetInfo.type) {
                        case Image:
                            if (AssetsManager.getInstance().addImage(assetInfo.path, assetInfo.name) == null) {
                                JOptionPane.showMessageDialog(null, "No se encontro el recurso " + assetInfo.path,
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    i++;
                }
                return null;
            }

            @Override
            protected void done() {
                dialog.dispose();// close the modal dialog
            }
        };
        sw.execute();

        dialog.setVisible(true);
    }
}
