import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener, ComponentListener
{
	
	public static final String ROOMS_COUNT = "0";
	public static final String SINGLE_BEDROOM_COUNT = "1";
	public static final String DOUBLE_BEDROOM_COUNT = "2";
	public static final String BOOKED_ROOM = "YES/NO";
	public static final String AVAILABLE_ROOM = "Yes/NO";

	

	private String file;
	private AboutDialog aboutDlg;
	private boolean hotelUpdated = false;
	private Vector registeredListeners = new Vector();
	private RoomCollection roomCollection;
	private JPanel topPanel = new JPanel(new BorderLayout());
	private JPanel titlePanel = new JPanel(new GridLayout(2, 1));
	private JLabel pictureLabel = new JLabel();
	private JLabel hotelLabel = new JLabel("XYZ Hotel Management System", JLabel.CENTER);
	private JLabel xyzLabel = new JLabel("Welcome to XYZ Hotel", JLabel.CENTER);
	private JTabbedPane theTab = new JTabbedPane(JTabbedPane.TOP);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("About");
	private JMenuItem aboutItem = new JMenuItem("About");
	private JMenuItem exitItem = new JMenuItem("Exit");
	private WindowCloser closer = new WindowCloser();

	public HotelManagementSystem(String f)
	{
		super("Hotel Management System");

		addWindowListener(closer);
		addComponentListener(this);

		setSize(800, 480);

		Container c = getContentPane();
		roomCollection = new RoomCollection();

		file = f;

		try
		{
			roomCollection.loadCars(file);
		}
		catch (java.io.FileNotFoundException exp)
		{
			System.out.println("The data file, 'hotel.dat' doesn't exist. Plase create an empty file named 'hotel.dat'");
			System.exit(0);
		}
		catch (java.io.EOFException exp){}
		catch (java.io.IOException exp)
		{
			System.out.println("The data file, 'hotel.dat' is possibly corrupted. Please delete it and create a new empty data file named hotel.dat");
			System.exit(0);
		}
		catch (Exception exp)
		{
			System.out.println("There was an error loading 'hotel.dat'. Try deleting and creating a new empty file named 'hotel.dat'");
			System.exit(0);
		}

		String currentFont = hotelLabel.getFont().getName();
		hotelLabel.setFont(new Font(currentFont, Font.BOLD, 26));
		xyzLabel.setFont(new Font(currentFont, Font.PLAIN, 16));

		menuBar.add(fileMenu);
		fileMenu.add(aboutItem);
		fileMenu.add(exitItem);
		aboutItem.addActionListener(this);


		setJMenuBar(menuBar);

		pictureLabel.setIcon(new ImageIcon("vu.png"));
		titlePanel.add(hotelLabel);
		titlePanel.add(xyzLabel);
		topPanel.add(pictureLabel, "East");
		topPanel.add(titlePanel, "West");

		AdminPanel welcomePanel = new AdminPanel(this, f);
		RearrangementForm addCarPanel = new RearrangementForm(this);
		RegistrationForm showAllCarsPanel = new RegistrationForm(this);
		FeedbackPanel feedbackPanel = new FeedbackPanel(this);
		BrowsingAvailability searchByOtherPanel = new BrowsingAvailability(this);

		theTab.add("Report for hotel staff", welcomePanel);
		theTab.add("Rearrangment(For staff)", addCarPanel);
		theTab.add("Enter customer details", showAllCarsPanel);
		theTab.add("Customer's feedback", feedbackPanel);
		theTab.add("Search on price and Hour", searchByOtherPanel);

		theTab.addChangeListener(showAllCarsPanel);
		theTab.addChangeListener(welcomePanel);

		theTab.setSelectedIndex(0);

		c.setLayout(new BorderLayout());
		c.add(topPanel, "North");
		c.add(theTab, "Center");
	}

	public void aboutMenuItemClicked()
	{
		
		if (aboutDlg == null)
			aboutDlg = new AboutDialog(this, "About", true);
		aboutDlg.showAbout();
	}

	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == aboutItem)
			aboutMenuItemClicked();
		else if (ev.getSource() == exitItem)
			closing();
	}

	
	public void addCarUpdateListener(Object listener)
	{
		if (!(listener == null))
			registeredListeners.add(listener);
	}

	
	public int addNewRoom(HotelRoom c)
	{
		return roomCollection.addCar(c);
	}

	public void closing()
	{
		boolean ok;

		if (hotelUpdated)
		{
			do
			{
				try
				{
					roomCollection.saveCars(file);
					ok = true;
				}
				catch (java.io.IOException exp)
				{
					int result = JOptionPane.showConfirmDialog(this, "The data file could not be written, possibly because you don't have access to this location.\nIf you chose No to retry you will lose all car data from this session.\n\nWould you like to reattempt saving the data file?", "Problem saving data", JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION)
						ok = false;
					else
						ok = true;
				}
			}
			while (!ok);
		}

		System.exit(0);
	}

	public void componentHidden(ComponentEvent ev) {}

	public void componentMoved(ComponentEvent ev) {}

	
	public void componentResized(ComponentEvent ev)
	{
		if (this == ev.getComponent())
		{
			Dimension size = getSize();

			if (size.height < 530)
				size.height = 530;
			if (size.width < 675)
				size.width = 675;

			setSize(size);
		}
	}

	public void componentShown(ComponentEvent ev) {}

	
	public static double[] convertToRange(String s)
	{
		String[] parts = s.trim().split("-");
		double[] bounds = new double[2];

		try
		{
			if (parts.length == 1)
			{
				String c = s.substring(s.length() - 1);

				
				if (c.equals("+"))
				{
					
					bounds[0] = Double.parseDouble(s.substring(0, s.length() - 1));
					
					bounds[1] = -1;
				}
				else
				{
					
					bounds[0] = Double.parseDouble(s);
					bounds[1] = bounds[0];
				}
			}
			
			else if(parts.length == 2)
			{
				bounds[0] = Double.parseDouble(parts[0]);
				bounds[1] = Double.parseDouble(parts[1]);
				if (bounds[0] > bounds[1])
				{
					
					bounds[0] = -1;
					bounds[1] = -1;
				}
			}
		}
		catch (NumberFormatException exp)
		{
			
			bounds[0] = -1;
			bounds[1] = -1;
		}

		return bounds;
	}

	
	public HotelRoom[] getAllRooms()
	{
		return roomCollection.getAllCars();
	}


	
	public boolean getRoomsUpdated()
	{
		return hotelUpdated;
	}

	
	public String getStatistics(String type)
	{
		String result = null;

		if (type == ROOMS_COUNT)
		{
			result	= roomCollection.roomsCount();
		}
		else if (type == SINGLE_BEDROOM_COUNT)
		{
			result = roomCollection.singleBedroomCount();
		}
		else if (type == DOUBLE_BEDROOM_COUNT)
		{
			result = roomCollection.getDoubleBedRoom();
		}
		else if (type == BOOKED_ROOM)
		{
			result = roomCollection.getBookedRoom();
		}
		else if (type == AVAILABLE_ROOM)
		{
			result = roomCollection.getAvailableRoom();
		}

		return result;
	}

	
	public static void main(String[] args)
	{
		HotelManagementSystem carSales = new HotelManagementSystem("hotel.dat");
		carSales.setVisible(true);
	}

	
	public HotelRoom[] search(int minAge, int maxAge)
	{
		return roomCollection.search(minAge, maxAge);
	}

	
	public HotelRoom[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
	{
		return roomCollection.search(minPrice, maxPrice,  minDistance, maxDistance);
	}

	
	public void setRoomsUpdated()
	{
		hotelUpdated = true;

		for (int i = 0; i < registeredListeners.size(); i++)
		{
			Class[] paramType = {RoomUpdateEvent.class};
			Object[] param = {new RoomUpdateEvent(this)};

			try
			{
				
				java.lang.reflect.Method callingMethod = registeredListeners.get(i).getClass().getMethod("hotelUpdated", paramType);
				
				callingMethod.invoke(registeredListeners.get(i), param);
			}
			catch (NoSuchMethodException exp)
			{
				System.out.println("Warning, 'public HotelUpdated method does not exist in " + registeredListeners.get(i).getClass().getName() + ". You will not receive any hotel update events");
			}
			catch (IllegalAccessException exp)
			{
				System.out.println("Warning, the 'public hotelUpdated' method couldn't be called for unknown reasons, You will not receive any hotel update events");
			}
			catch (Exception exp){}
		}
	}

	
	
	public static HotelRoom[] vectorToCar(Vector v)
	{
		HotelRoom[] ret = new HotelRoom[v.size()];

		for (int i = 0; i < v.size(); i++)
		{
			ret[i] = (HotelRoom)v.elementAt(i);
		}

		return ret;
	}

	class WindowCloser extends WindowAdapter
	{
		
		public void windowClosing(WindowEvent ev)
		{
			closing();
		}
	}
}