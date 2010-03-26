package test;

import java.io.IOException;
import java.util.Collection;

import javax.bluetooth.BluetoothStateException;

public class TestClient {
	private static final String UUID = "E6FEC3B275744C079B2F8883DBE38937";
	
	public static void main( String args[] )
	{
		bluetooth.BluetoothDevices devices;
		Collection<bluetooth.BluetoothDevice> deviceCollection;
		
		System.out.println( "Starting client test.\nDiscovering devices and services..." );
		
		try
		{
			devices = new bluetooth.BluetoothDevices();
		}
		catch ( BluetoothStateException e )
		{
			e.printStackTrace();
			System.out.println( "Error initializing devices." );
			return;
		}
		
		try
		{
			devices.discoverDevicesAndServices( UUID );
		}
		catch ( BluetoothStateException e )
		{
			System.out.println( "No dice." );
		}
		
		System.out.println( "Done discovery" );
		
		deviceCollection = devices.getDevicesCollection();
		
		for ( bluetooth.BluetoothDevice device : deviceCollection )
		{
			System.out.println( "Device " + device.name() + " at address " + device.address() );
			
			/*
			try 
			{
				device.connect();
				System.out.println("Connected to " + device.address() + ".");
			} 
			catch (BluetoothStateException e) 
			{
				e.printStackTrace();
				System.out.println( "This is bad, the devices service wasn't found." );
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.out.println( "Something unknown to us happened." );
			}
			
			if ( device.isConnected() )
			{
			*/
				String requests[] = {"umanitoba.ca", "google.com"};
				
				for ( String request : requests )
				{
					System.out.println("Sending request: " + request );
					
					byte[] response = null;
					
					try
					{
						response = device.request( request.getBytes() );
					}
					catch ( IOException e )
					{
						System.out.println( "Error with request" );
					}
					
					if ( response != null )
					{
						int bytes=0;
						while(response[bytes] != 0){
							bytes++;
						}
						String result = new String(response, 0, bytes);
		
						System.out.println("Recieved: " + result);
					}
				}
			//}
		}
		
		System.out.println( "Done testing client." );
	}
	/*public static void main(String arg[]) throws Exception {
		BluetoothNode testNode = new BluetoothNode();
		
		testNode.discoverTetherServices();
		HashMap<String, String> services = testNode.getTetherServices();
		
		if(services.entrySet().size() > 0){
			Entry<String, String> service = services.entrySet().iterator().next();
			String address = service.getKey();
			
			testNode.connectToService(address);
			System.out.println("Connected to " + address + ".");
			
			String request = "google.com";
			
			System.out.println("Sending request: " + request);
			byte[] response = testNode.sendRequest(request.getBytes());
			
			int bytes=0;
			while(response[bytes] != 0){
				bytes++;
			}
			String result = new String(response, 0, bytes);

			System.out.println("Recieved: " + result);
		}
		
		// to close the simulator
		//BCC.closeSimulator();
	}*/
}
