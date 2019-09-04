package chapter04.serial;

import java.io.*;
import java.util.*;

import chapter04.serial.util.ArrayUtils;
import chapter04.serial.util.ByteUtils;
import chapter04.serial.util.ShowUtils;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 串口管理
 * @author liu
 */
public final class SerialHandler {
	/**
	 * 禁止实列化
	 */
	private SerialHandler() {

	}

	/**
	 * 查找所有的可用串口
	 *
	 * @return 可用串口名称列表
	 */
	public static Set<String> listPorts() {
		// 获得当前所有可用串口
		Enumeration<CommPortIdentifier> comport = CommPortIdentifier.getPortIdentifiers();
		Set<String> set = new HashSet<String>();

		// 将可用串口名添加到List并返回该List
		while (comport.hasMoreElements()) {
			set.add(comport.nextElement().getName());
		}

		return set.isEmpty() ? null : set;
	}

	/**
	 * 建立连接
	 *
	 * @param portName 串口名词
	 * @param baudrate 波特率
	 * @param data     数据位
	 * @param stop     停止位
	 * @param parity   奇偶校验位
	 * @return 串口对象
	 * @throws PortInUseException                串口被占用
	 * @throws NoSuchPortException               串口不可用
	 * @throws UnsupportedCommOperationException 参数错误
	 * @throws IllegalStateException 逻辑异常
	 * @throws IllegalArgumentException 参数异常
	 */
	public static SerialPort connect(String portName, int baudrate, int data, int stop, int parity)
			throws PortInUseException, NoSuchPortException, UnsupportedCommOperationException,IllegalStateException,IllegalArgumentException
	{

		CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(portName);
		CommPort commPort = identifier.open(portName, 2000);

		if (commPort instanceof SerialPort) {
			SerialPort serialPort = (SerialPort) commPort;

			Integer databits = null;
			switch (data)
			{
				case  5:databits = SerialPort.DATABITS_5;break;
				case  6:databits = SerialPort.DATABITS_6;break;
				case  7:databits = SerialPort.DATABITS_7;break;
				case  8:databits = SerialPort.DATABITS_8;break;
				default: throw new IllegalArgumentException("databists is not legal");
			}

			Integer stopbits = null;
			switch (stop)
			{
				case 1 : stopbits = SerialPort.STOPBITS_1;break;
				case 2 : stopbits = SerialPort.STOPBITS_2;break;
				case 3 : stopbits = SerialPort.STOPBITS_1_5;break;
				default: throw new IllegalStateException("stopbits is not legal");
			}

			Integer paritybits = null;
			switch (parity)
			{
				case 0 : paritybits = SerialPort.PARITY_NONE;break;
				case 1 : paritybits = SerialPort.PARITY_ODD;break;
				case 2 : paritybits = SerialPort.PARITY_EVEN;break;
				case 3 : paritybits = SerialPort.PARITY_MARK;break;
				default: throw new IllegalStateException("parity is not legal");
			}

			serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			return serialPort;
		}
		throw new IllegalStateException();
	}

	/**
	 * 建立连接
	 *
	 * @param portName 串口名词
	 * @param baudrate 波特率
	 * @return 串口对象
	 * @throws PortInUseException                串口被占用
	 * @throws NoSuchPortException               串口不可用
	 * @throws UnsupportedCommOperationException 参数错误
	 * @throws IllegalStateException 逻辑异常
	 * @throws IllegalArgumentException 参数异常
	 */
	public static SerialPort connect(String portName, int baudrate) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
		return connect(portName,baudrate,8,1,0);
	}

	/**
	 * 关闭连接
	 * @param serialPort 关闭对象
	 */
	public static void close(SerialPort serialPort)
	{
		if (serialPort != null)
		{
			serialPort.close();
		}
	}

	/**
	 * 写数据
	 * @param serialPort 串口对象
	 * @param msg 写入的数据
	 * @throws IOException 抛出的异常
	 * @throws IllegalArgumentException 参数异常
	 */
	public static void write(SerialPort serialPort, byte[] msg)  throws IOException,IllegalArgumentException
	{
		if( msg == null)
		{
			throw new IllegalArgumentException("msg can not be null or empty");
		}

		try
		(
			OutputStream os = serialPort.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
		)
		{
			bos.write(msg);
			bos.flush();
		}
		catch (IOException e)
		{
			throw  e;
		}
	}

	/**
	 * 写数据
	 * @param serialPort 串口对象
	 * @param msg 写入的数据
	 * @throws IOException 抛出的异常
	 * @throws IllegalArgumentException 参数异常
	 */
	public static void write(SerialPort serialPort,String msg) throws IOException,IllegalArgumentException
	{
		write(serialPort, ByteUtils.StringToByte(msg));
	}

	/**
	 * 读取数据
	 * @param serialPort
	 * @return
	 * @throws IOException
	 */
	private static byte[] read(SerialPort serialPort) throws IOException
	{
		byte[] result = new byte[256];
		try
		(
			InputStream is = serialPort.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
		)
		{
			while(bis.read() > 0);
		}

		for(int i=result.length;i>=0;i--)
		{
			if(result[i] != 0)
			{
				return Arrays.copyOfRange(result,0,i+1);
			}
		}

		return result;
	}

	public static void read(SerialPort serialPort ,byte[] buffer) throws IOException
	{
		buffer = read(serialPort);
	}

	public static void read(SerialPort serialPort ,String msg) throws IOException
	{
		msg = new String(read(serialPort));
	}


	/**
	 * 从串口读取数据
	 *
	 * @param serialPort
	 *            当前已建立连接的SerialPort对象
	 * @return 读取到的数据
	 */
	public static byte[] readFromPort(SerialPort serialPort) {
		InputStream in = null;
		byte[] bytes = {};
		try {
			in = serialPort.getInputStream();
			// 缓冲区大小为一个字节
			byte[] readBuffer = new byte[1];
			int bytesNum = in.read(readBuffer);
			while (bytesNum > 0) {
				bytes = ArrayUtils.concat(bytes, readBuffer);
				bytesNum = in.read(readBuffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}


	public static void addListener(SerialPort serialPort, DataAvailableListener listener) throws TooManyListenersException
	{
		// 给串口添加监听器
		serialPort.addEventListener(new SerialPortListener(listener));
		// 设置当有数据到达时唤醒监听接收线程
		serialPort.notifyOnDataAvailable(true);
		// 设置当通信中断时唤醒中断线程
		serialPort.notifyOnBreakInterrupt(true);
	}

	/**
	 * 串口监听
	 */
	public static class SerialPortListener implements SerialPortEventListener {

		private DataAvailableListener mDataAvailableListener;

		public SerialPortListener(DataAvailableListener mDataAvailableListener) {
			this.mDataAvailableListener = mDataAvailableListener;
		}

		public void serialEvent(SerialPortEvent serialPortEvent) {
			switch (serialPortEvent.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE: // 1.串口存在有效数据
				if (mDataAvailableListener != null) {
					mDataAvailableListener.dataAvailable();
				}
				break;

			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2.输出缓冲区已清空
				break;

			case SerialPortEvent.CTS: // 3.清除待发送数据
				break;

			case SerialPortEvent.DSR: // 4.待发送数据准备好了
				break;

			case SerialPortEvent.RI: // 5.振铃指示
				break;

			case SerialPortEvent.CD: // 6.载波检测
				break;

			case SerialPortEvent.OE: // 7.溢位（溢出）错误
				break;

			case SerialPortEvent.PE: // 8.奇偶校验错误
				break;

			case SerialPortEvent.FE: // 9.帧错误
				break;

			case SerialPortEvent.BI: // 10.通讯中断
				ShowUtils.errorMessage("与串口设备通讯中断");
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 串口存在有效数据监听
	 */
	public interface DataAvailableListener {
		/**
		 * 串口存在有效数据
		 */
		void dataAvailable();
	}
}
