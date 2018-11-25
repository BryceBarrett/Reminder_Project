
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Reminder {

    private static int count;
    private static Semaphore countSem = new Semaphore(1);
    private static List<Reminder> reminderList = deserializeList();
    private Thread thread;
    private Time time;
    private String message;

    public Reminder(String message, Time time) {
        this.time = time;
        this.message = message;
        thread = new Thread(() -> {
            try {
                countSem.acquire();
                count++;
                countSem.release();
                Thread.sleep(time.getTime() - Time.valueOf(LocalTime.now()).getTime());
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon icon = new TrayIcon(image);
                tray.add(icon);
                icon.displayMessage("Reminder", message, TrayIcon.MessageType.WARNING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void cancel() {
        thread.interrupt();
    }

    public static int getCount() {
        return count;
    }

    public static List<Reminder> getReminderList() {
        return reminderList;
    }

    public static void setReminderList(List<Reminder> reminderList) {
        Reminder.reminderList = reminderList;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void joinAllThreads() {
        for (Reminder rem : reminderList) {
            try {
                if (rem.getThread().isAlive()) {
                    rem.getThread().join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean equals(Reminder ob) {
        boolean retVal = (ob instanceof Reminder);
        if (retVal) {
            Reminder other = (Reminder) ob;
            retVal = other.getTime().equals(time) && other.getMessage().equals(message);
        }
        return retVal;
    }

    private static List<Reminder> deserializeList() {
        List<Reminder> retVal;
        try {
            File file = new File(File.separator + System.getProperty("user.home")
                    + File.separator + "Reminder_Project_Barrett");
            if (file.exists() && !file.isDirectory()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                retVal = (ArrayList<Reminder>) ois.readObject();
            } else {
                retVal = new ArrayList<>();
            }
            for (Reminder rem : retVal) {
                if (rem.getThread() != null && rem.getThread().isAlive()) {
                    rem.getThread().interrupt();
                    rem.setThread(new Thread(() -> {
                        try {
                            countSem.acquire();
                            count++;
                            countSem.release();
                            Thread.sleep(rem.getTime().getTime() - Time.valueOf(LocalTime.now()).getTime());
                            SystemTray tray = SystemTray.getSystemTray();
                            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                            TrayIcon icon = new TrayIcon(image);
                            tray.add(icon);
                            icon.displayMessage("Reminder", rem.getMessage(), TrayIcon.MessageType.WARNING);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }));
                    rem.getThread().start();
                } else {
                    rem.setThread(new Thread(() -> {
                        try {
                            countSem.acquire();
                            count++;
                            countSem.release();
                            Thread.sleep(rem.getTime().getTime() - Time.valueOf(LocalTime.now()).getTime());
                            SystemTray tray = SystemTray.getSystemTray();
                            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                            TrayIcon icon = new TrayIcon(image);
                            tray.add(icon);
                            icon.displayMessage("Reminder", rem.getMessage(), TrayIcon.MessageType.WARNING);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }));
                    rem.getThread().start();
                }
            }
        } catch (Exception e) {
            retVal = new ArrayList<>();
            System.out.println("An error occured while deserializing the reminder list");
        }

        return retVal;
    }

    private static void serializeList() {
        try {
            File file = new File(File.separator + System.getProperty("user.home")
                    + File.separator + "Reminder_Project_Barrett");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(reminderList);
        } catch (Exception e) {
            System.out.println("Reminder List serializing issue occured");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Reminder reminder = new Reminder("Testing", Time.valueOf(LocalTime.now()));
        getReminderList().add(reminder);
        joinAllThreads();
    }
}
