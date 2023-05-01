import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        GamePanel panel = new GamePanel();  // GamePanel adlı bir JPanel nesnesi oluşturuldu.

        this.add(panel);  // JPanel nesnesi JFrame'e eklendi.
        this.setTitle("Snake");  // JFrame penceresinin başlığı ayarlandı.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Pencerenin kapatılması için varsayılan davranış ayarlandı.
        this.setResizable(false);  // Pencerenin boyutunun değiştirilememesi için ayarlandı.
        this.pack();  // JFrame penceresi, içeriklerine göre boyutlandırıldı.
        this.setVisible(true);  // JFrame penceresi görünür hale getirildi.
        this.setLocationRelativeTo(null);  // JFrame penceresinin konumu ayarlandı (null değeri, pencerenin ekran ortasında olacağı anlamına gelir).
    }
}
