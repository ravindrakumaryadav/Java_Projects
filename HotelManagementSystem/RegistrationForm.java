import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class RegistrationForm extends JPanel implements ActionListener, ChangeListener
{
	private HotelManagementSystem hotelManagementSystem;
	private HotelRoom[] roomsList;
	private int currentIndex = 0;
	private JLabel headingLabel = new JLabel("Customer Registration Form");
	private JButton previousButton = new JButton("Previous");
	private JButton nextButton = new JButton("Next");
	private JPanel buttonPanel = new JPanel();
	private HotelRoomDetails roomsComponents = new HotelRoomDetails();
	private boolean carsUpdated = false;

	public RegistrationForm(HotelManagementSystem hotelSys)
	{
		hotelManagementSystem = hotelSys;
		roomsList = hotelManagementSystem.getAllRooms();

		if (roomsList.length > 0)
			roomsComponents.displayDetails(roomsList[0]);

		hotelSys.addCarUpdateListener(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		headingLabel.setAlignmentX(0.5f);

		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);

		add(Box.createVerticalStrut(10));
		add(headingLabel);
		add(Box.createVerticalStrut(15));
		roomsComponents.add(buttonPanel, "Center");
		add(roomsComponents);

		roomsList = hotelManagementSystem.getAllRooms();
	}

	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == previousButton)
			previousButtonClicked();
		else if (ev.getSource() == nextButton)
			nextButtonClicked();
	}

	
	public void roomsUpdated(RoomUpdateEvent ev)
	{
		if (ev.getSource() == hotelManagementSystem)
		{
			
			carsUpdated = true;
		}
	}

	
	public void stateChanged(ChangeEvent ev)
	{
		
		if (ev.getSource() instanceof JTabbedPane)
		{
			JTabbedPane tab = (JTabbedPane)ev.getSource();
			
			if (tab.getSelectedIndex() == 2)
			{
				
				if (carsUpdated)
				{
					roomsList = hotelManagementSystem.getAllRooms();
					if (!(roomsList == null))
						roomsComponents.displayDetails(roomsList[currentIndex]);
					
					carsUpdated = false;
				}
			}
		}
	}

	
	private void nextButtonClicked()
	{
		if (currentIndex < roomsList.length - 1)
		{
			currentIndex++;
			roomsComponents.displayDetails(roomsList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(hotelManagementSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	
	private void previousButtonClicked()
	{
		if (currentIndex > 0)
		{
			currentIndex--;
			roomsComponents.displayDetails(roomsList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(hotelManagementSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}
}