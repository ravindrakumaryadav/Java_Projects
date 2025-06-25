import java.util.*;

public class NumberOfRooms implements java.io.Serializable
{
	private String manufacturer;		
	private HotelRoom[] car = new HotelRoom[0];		
	
	public NumberOfRooms(String nam, HotelRoom c)
	{
		manufacturer = nam.toUpperCase();
		addCar(c);
	}

	
	public void addCar(HotelRoom c)
	{
		car = resizeArray(car, 1);
		car[car.length - 1] = c;
	}

	
	public int carCount()
	{
		return car.length;
	}

	
	public HotelRoom[] getAllCars()
	{
		return car;
	}



	public String getManufacturerName()
	{
		return manufacturer;
	}

	
	private HotelRoom[] resizeArray(HotelRoom[] c, int extendBy)
	{
		HotelRoom[] result = new HotelRoom[c.length + extendBy];

		for (int i = 0; i < c.length; i++)
		{
			result[i] = c[i];
		}

		return result;
	}

	
	public void setManufacturersName(String nam)
	{
		manufacturer = nam.toUpperCase();
	}
}