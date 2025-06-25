import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class AdminPanel extends JPanel implements ChangeListener
{
	private HotelManagementSystem hotelSystem,hms;
	private JLabel headingLabel = new JLabel("Welcome to Admin panel", JLabel.CENTER);
	private JLabel hotelLabel = new JLabel();
	private JLabel singlBedRoomLabel = new JLabel();
	private JLabel doubleBedRoomLabel = new JLabel();
	private JLabel bookedRoomLabel = new JLabel();
	private JLabel availableRoomLabel = new JLabel();
	private JLabel versionLabel = new JLabel();
	private JLabel facilatiesLabel = new JLabel();
	private JPanel statsPanel = new JPanel();
	private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
	private boolean roomsUpdated = false;
	private String file;

	public AdminPanel(HotelManagementSystem hotelSys, String data)
	{
		hotelSystem = hotelSys;
		file = data;
		setLayout(new BorderLayout(0, 10));
		hotelSys.addCarUpdateListener(this);

		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		centerPanel.add(statsPanel);
		headingLabel.setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));

		updateStats();

		statsPanel.add(hotelLabel);
		statsPanel.add(singlBedRoomLabel);
		statsPanel.add(doubleBedRoomLabel);
		statsPanel.add(bookedRoomLabel);
		statsPanel.add(availableRoomLabel);
		statsPanel.add(Box.createVerticalStrut(20));
		statsPanel.add(facilatiesLabel);

		add(headingLabel, "North");
		add(centerPanel, "Center");
	}

	public void carsUpdated(RoomUpdateEvent ev)
	{
		if (ev.getSource() == hotelSystem)
		{
			roomsUpdated = true;
		}
	}

	
	public void stateChanged(ChangeEvent ev)
	{
		if (ev.getSource() instanceof JTabbedPane)
		{
			JTabbedPane tab = (JTabbedPane)ev.getSource();
			
			if (tab.getSelectedIndex() == 0)
			{
				
				if (roomsUpdated)
				{
					updateStats();
					roomsUpdated = false;
				}
			}
		}
	}

	
	private void updateStats()
	{
		
		String roomCount = hotelSystem.getStatistics(hms.ROOMS_COUNT);
		String singleBedrromCount = hotelSystem.getStatistics(hms.SINGLE_BEDROOM_COUNT);
		String doubleBedroomCount = hotelSystem.getStatistics(hms.DOUBLE_BEDROOM_COUNT);
		String bookedRoom = hotelSystem.getStatistics(hms.BOOKED_ROOM);
		String availableRoom = hotelSystem.getStatistics(hms.AVAILABLE_ROOM);
		java.io.File f = new java.io.File(file);
		long size = f.length(); 

		hotelLabel.setText("Total number of rooms: " + String.valueOf(roomCount));
		singlBedRoomLabel.setText("Single bed room: " + String.valueOf(singleBedrromCount));
		doubleBedRoomLabel.setText("Double bed room: " + String.valueOf(doubleBedroomCount));
		bookedRoomLabel.setText("Booked room: " + String.valueOf(bookedRoom));
		availableRoomLabel.setText("Available room: " + String.valueOf(availableRoom));
		facilatiesLabel.setText("Facialities: " + size + " bytes");
	}
}