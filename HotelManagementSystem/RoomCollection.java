import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class RoomCollection
{
	
	public static final int NO_ERROR = 0;
	
	public static final int CARS_MAXIMUM_REACHED = 1;
	
	public static final int MANUFACTURERS_MAXIMUM_REACHED = 2;

	private final int maxManufacturers = 20;
	private final int maxCars = 20;

	private NumberOfRooms[] numberOfROOMs = new NumberOfRooms[0];

	public RoomCollection(){}

	public RoomCollection(NumberOfRooms man)
	{
		addManufacturer(man);
	}

	
	public int addCar(HotelRoom c)
	{
		NumberOfRooms man;
		String name = c.getManufacturer();
		int index = -1;
		int result = NO_ERROR;

		for (int i = 0; i < numberOfROOMs.length; i++)
		{
			if (numberOfROOMs[i].getManufacturerName().equalsIgnoreCase(name))
				index = i;
		}

		if (index == -1)
		{
			if (numberOfROOMs.length < maxManufacturers)
			{
				man = new NumberOfRooms(name, c);
				addManufacturer(man);
			}
			else
				result = MANUFACTURERS_MAXIMUM_REACHED;
		}
		else
		{
			if (numberOfROOMs[index].carCount() < maxCars)
				numberOfROOMs[index].addCar(c);
			else
				result = CARS_MAXIMUM_REACHED;
		}

		return result;
	}

	
	private void addManufacturer(NumberOfRooms man)
	{
		numberOfROOMs = resizeArray(numberOfROOMs, 1);
		numberOfROOMs[numberOfROOMs.length - 1] = man;
	}

	
	public String roomsCount()
	{
		int count = 0;

		for (int i = 0; i < numberOfROOMs.length; i++)
			count += numberOfROOMs[i].carCount();

		Integer i=new Integer(count);
		
		return String.valueOf(i);
	}

	
	public String singleBedroomCount()
	{
		
		Integer i=new Integer(numberOfROOMs.length);
		return String.valueOf(i);
	}

	
	public HotelRoom[] getAllCars()
	{
		Vector result = new Vector();
		HotelRoom[] car;
		for (int i = 0; i < numberOfROOMs.length; i++)
		{
			car = numberOfROOMs[i].getAllCars();
			for (int j = 0; j < car.length; j++)
			{
				result.addElement(car[j]);
			}
		}

		return HotelManagementSystem.vectorToCar(result);
	}

	public NumberOfRooms[] getAllSingleBedRoomCount()
	{
		
		return numberOfROOMs;
	}

	
	public String getAvailableRoom()
	{
		Integer i=new Integer(numberOfROOMs.length);
		return String.valueOf(i);
	}

	public String getDoubleBedRoom()
	{
		Integer i=new Integer(numberOfROOMs.length);
		return String.valueOf(i);
	}

	
	public String getBookedRoom()
	{
		Integer i=new Integer(numberOfROOMs.length);
		return String.valueOf(i);	
	}
	
	public void loadCars(String file) throws IOException, ClassNotFoundException
	{

		ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file));
		numberOfROOMs = (NumberOfRooms[])inp.readObject();
		inp.close();
	}

	
	private NumberOfRooms[] resizeArray(NumberOfRooms[] inArray, int extendBy)
	{
		NumberOfRooms[] result = new NumberOfRooms[inArray.length + extendBy];

		for (int i = 0; i < inArray.length; i++)
		{
			result[i] = inArray[i];
		}

		return result;
	}

	
	public void saveCars(String file) throws IOException
	{
		int flag = 0;
		int items = numberOfROOMs.length;
		NumberOfRooms temp;

		if (numberOfROOMs.length > 0)
		{
			do
			{
				flag = 0;
				for (int i = 0; i < items; i++)
				{
					if (i + 1 < items)
					{
						if (numberOfROOMs[i].getManufacturerName().compareTo(numberOfROOMs[i + 1].getManufacturerName()) > 0)
						{
							temp = numberOfROOMs[i];
							numberOfROOMs[i] = numberOfROOMs[i + 1];
							numberOfROOMs[i + 1] = temp;
							flag++;
						}
					}
				}
			}
			while (flag > 0);

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

			out.writeObject(numberOfROOMs);
			out.close();
		}
	}

	
	public HotelRoom[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
	{
		Vector result = new Vector();
		int price;
		String bookedrooms;
		HotelRoom[] hotel;
		hotel = getAllCars();

		for (int i = 0; i < hotel.length; i++)
		{
			price = hotel[i].getPrice();
			bookedrooms = hotel[i].getBookedRoom();

			
		}

		return HotelManagementSystem.vectorToCar(result);
	}

	
	public HotelRoom[] search(int minAge, int maxAge)
	{
		HotelRoom[] car;
		car = getAllCars();
		Vector result = new Vector();
		if (maxAge == -1)
		{
			for (int i = 0; i < car.length; i++)
			{
				if (car[i].getAge() >= minAge)
				{
					result.addElement(car[i]);
				}
			}
		}
		else
		{
			for (int i = 0; i < car.length; i++)
			{
				if (car[i].getAge() >= minAge && car[i].getAge() <= maxAge)
				{
					result.addElement(car[i]);
				}
			}
		}

		return HotelManagementSystem.vectorToCar(result);
	}
}