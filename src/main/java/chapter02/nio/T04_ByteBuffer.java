package chapter02.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.junit.Test;

public class T04_ByteBuffer 
{
	/**
	 * 测试流程
	 * 写入数据到Buffer 生成的buffer默认是写模式
	 * 调用flip()方法 将写模式改成读模式
	 * 从Buffer中读取数据
	 * 调用clear()方法或者compact()方法
	 * 
	 * 当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
	 * 在读模式下，可以读取之前写入到buffer的所有数据。
	 * 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用clear()或compact()方法。
	 * clear()方法会清空整个缓冲区。
	 * compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
	 */
	@Test
	public void flow()
	{
		//生成buffer
		ByteBuffer buffer = ByteBuffer.allocate(48);
		
		//写入
		for(byte i=0;i<48;i++)
		{
			buffer.put(i);
		}
		
		//反转
		buffer.flip();
		while(buffer.remaining()>10)
		{
			System.out.print(buffer.get());
		}
		System.out.println();
		System.out.println("---------------------------------");
		
		//buffer.clear();//方法会清空整个缓冲区,变成写模式
		buffer.compact();//方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
		
		
		buffer.flip();
		while(buffer.hasRemaining())
		{
			System.out.print(buffer.get());
		}
		
	}
	
	/**
	 * 测试基本属性
	 * 
	 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。capacity position limit三个属性控制操作
	 * 
	 * 1.容量capacity:buffer的固定的大小值
	 * 
	 * 2.读写位置position 
	 * 写:position表示要写入的下一个位置,初始值=0,最大值=capacity – 1
	 * 读:position表示当前的位置，初始值=0,最大值=limit-1
	 * 
	 * 3.最大可读写limit
	 * 写:limit = capacity
	 * 读:limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值
	 */
	@Test
	public void attribute()
	{
		CharBuffer buf = CharBuffer.allocate(5);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.put('a');
		buf.put('b');
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		
		buf.flip();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.get();
		buf.get();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
	}
	
	/**
	 * 直接通过调整position和limit来读写切换
	 */
	@Test
	public void flip()
	{
		CharBuffer buf = CharBuffer.allocate(5);
		buf.put("abc"); //写模式
		
		//调整成读模式
		buf.limit(buf.position());
		buf.position(0);
		
		while(buf.hasRemaining())
		{
			System.out.println(buf.get());
		}
		
		buf.position(buf.limit());
		buf.limit(buf.capacity());
		buf.put("de");
		
		buf.flip();
		while(buf.hasRemaining())
		{
			System.out.println(buf.get());
		}
	}
	
	/**
	 * 数据的覆盖
	 *  limit = position;
        position = 0;
        mark = -1;
	 */
	@Test
	public void flip2()
	{
		ByteBuffer buf = ByteBuffer.allocate(5);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.put((byte)1);
		buf.put((byte)2);
		buf.put((byte)3);
		buf.put((byte)4);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.flip();
		buf.get();
		buf.get();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.flip();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
	}
	
	/**
	 * 初始化的情况
	 */
	@Test
	public void init()
	{
		//capacity=5 position=0 limit=5
		ByteBuffer buf = ByteBuffer.allocate(5);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		
		//capacity=5 position=0 limit=5
		ByteBuffer buf2 = ByteBuffer.wrap("abcde".getBytes());
		System.out.println("capacity="+buf2.capacity()+" position="+buf2.position()+" limit="+buf2.limit());
		
	}
	
	/**
	 * 测试数组
	 */
	@Test
	public void array()
	{
		//直接生成
		ByteBuffer buf = ByteBuffer.wrap("你好啊".getBytes());
		System.out.println("---------------------------------------------------------");
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		//获取数组,什么都不影响
		buf.array();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
	}
	
	/**
	 * 测试rewind
	 * postion重新置0,包括读写
	 */
	@Test
	public void rewindWrite()
	{
		//直接生成
		ByteBuffer buf = ByteBuffer.allocate(10);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		buf.put((byte)1);
		buf.put((byte)2);
		buf.put((byte)3);
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		System.out.println(Arrays.toString(buf.array()));
		buf.rewind();
		System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
		
		buf.put((byte) 0);
		buf.put((byte) 0);
		buf.put((byte) 0);
		System.out.println(Arrays.toString(buf.array()));
	}
	
	@Test
	public void rewindRead()
	{
		//直接生成
		ByteBuffer buf = ByteBuffer.wrap("nihao".getBytes()); 
		for(int i=0;i<2;i++)
		{
			System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
			while(buf.hasRemaining())
			{
				System.out.print((char)buf.get());
			}
			System.out.println();
			System.out.println("capacity="+buf.capacity()+" position="+buf.position()+" limit="+buf.limit());
			buf.rewind();
			System.out.println("===============================================");
		}
	}
	
	/**
	 * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
	 * 之后可以通过调用Buffer.reset()方法恢复到这个position
	 * 必须要先mark
	 */
	@Test
	public void mark()
	{
		ByteBuffer buf = ByteBuffer.wrap("niXhao".getBytes());
		
		//buf.flip();
		//重复读取hao
		while(buf.hasRemaining())
		{
			byte b = buf.get();
			if( b == (byte)'X' )
			{
				buf.mark();
				break;
			}
		}
		
		buf.reset();
		while(buf.hasRemaining())
		{
			byte b = buf.get();
			System.out.print((char)b);
		}
		System.out.println();
		
		buf.reset();
		while(buf.hasRemaining())
		{
			byte b = buf.get();
			System.out.print((char)b);
		}
		System.out.println();
	}
	
	/**
	 * 复制
	 * 方法用于创建一个与原始缓冲区共享内容的新缓冲区。,二者相互不影响
	 * 新缓冲区的position，limit，mark和capacity都初始化为原始缓冲区的索引值，然而，它们的这些值是相互独立的
	 */
	@Test
	public void duplicate()
	{
		ByteBuffer oldOne = ByteBuffer.wrap("niXhao".getBytes());
		ByteBuffer newOne = oldOne.duplicate();
		System.out.println("old-->"+"capacity="+oldOne.capacity()+" position="+oldOne.position()+" limit="+oldOne.limit());
		System.out.println("new-->"+"capacity="+newOne.capacity()+" position="+newOne.position()+" limit="+newOne.limit());
		
		System.out.println("--------------------------------------------------------------------------------------------");
		newOne.get();newOne.get();newOne.get();
		System.out.println("old-->"+"capacity="+oldOne.capacity()+" position="+oldOne.position()+" limit="+oldOne.limit());
		System.out.println("new-->"+"capacity="+newOne.capacity()+" position="+newOne.position()+" limit="+newOne.limit());

		
	}
	
	/**
	 * 根据现有的缓冲区对象来创建一个子缓冲区，即在现有缓冲区上切出一片来作为一个新的缓冲区，
	 * 但现有的缓冲区与创建的子缓冲区在底层数组层面上是数据共享的，
	 * 也就是说，子缓冲区相当于是现有缓冲区的一个视图窗口
	 * 自缓冲是只读的
	 */
	@Test
	public void slice()
	{
		ByteBuffer  cb = ByteBuffer .wrap("111111".getBytes());
		cb.get();
		cb.get();
		System.out.println("cb-->"+"capacity="+cb.capacity()+" position="+cb.position()+" limit="+cb.limit());

		//根据limit和positin截取
		ByteBuffer  slice = cb.slice();
		System.out.println("slice-->"+"capacity="+slice.capacity()+" position="+slice.position()+" limit="+slice.limit());
		while(slice.hasRemaining())
		{
			System.out.print(slice.get());
		}
		System.out.println();
		System.out.println("slice-->"+"capacity="+slice.capacity()+" position="+slice.position()+" limit="+slice.limit());
		
		//修改cb
		cb.position(0);
		cb.limit(slice.capacity());
		cb.put("9999".getBytes());
		
		System.out.println("---------------------------------------------");
		slice.position(0);
		while(slice.hasRemaining())
		{
			System.out.print(slice.get()); //slice收到影响
		}

	}
	
	/**
	 * 只读副本
	 * 可以通过调用缓冲区的asReadOnlyBuffer()方法，将任何常规缓冲区转 换为只读缓冲区，
	 * 这个方法返回一个与原缓冲区完全相同的缓冲区，并与原缓冲区共享数据，
	 * 只不过它是只读的。如果原缓冲区的内容发生了变化，只读缓冲区的内容也随之发生变化.
	 * 无法写入数据如果尝试修改只读缓冲区的内容，则会报ReadOnlyBufferException异常
	 * 
	 * asReadOnlyBuffer之前buffer要进入读状态，慎用。很危险
	 */
	@Test
	public void readOnly()
	{
		ByteBuffer bb = ByteBuffer.allocate(6);
		bb.put((byte)1);
		bb.put((byte)2);
		
		bb.flip();
		while(bb.hasRemaining())
		{
			System.out.print(bb.get());
		}
		System.out.println();
		
		bb.rewind();
		ByteBuffer only = bb.asReadOnlyBuffer();
		//System.out.println(Arrays.toString(only.array()));
		while(only.hasRemaining())
		{
			System.out.print(only.get());
		}
		System.out.println();
		
		bb.position(2);
		bb.limit(6);
		bb.put((byte)3);
		bb.put((byte)4);
		bb.put((byte)5);
		bb.put((byte)6);
		
		only.position(0);
		only.limit(6);
		while(only.hasRemaining())
		{
			System.out.println(only.get());
		}
		System.out.println();
	}
	
	/**
	 *  直接缓冲区
	 * 直接缓冲区是为加快I/O速度，使用一种特殊方式为其分配内存的缓冲区，
	 * JDK文档中的描述为：给定一个直接字节缓冲区，Java虚拟机将尽最大努 力直接对它执行本机I/O操作。
	 * 也就是说，它会在每一次调用底层操作系统的本机I/O操作之前(或之后)，
	 * 尝试避免将缓冲区的内容拷贝到一个中间缓冲区中 或者从一个中间缓冲区中拷贝数据。
	 * 要分配直接缓冲区，需要调用allocateDirect()方法，而不是allocate()方法，使用方式与普通缓冲区并无区别
	 */
	@Test
	public void direct()
	{
		int MAX_VALUE = Integer.MAX_VALUE/4;
		//比较一下速度
		ByteBuffer ib = ByteBuffer.allocate(MAX_VALUE);
		long now = System.currentTimeMillis();
		for(int i=0;i<MAX_VALUE;i++)
		{
			ib.put((byte)i);
		}
		System.out.println("allocate="+(System.currentTimeMillis()-now));
		

		ByteBuffer ib2 = ByteBuffer.allocateDirect(MAX_VALUE);
		now = System.currentTimeMillis();
		for(int i=0;i<MAX_VALUE;i++)
		{
			ib2.put((byte)i);
		}
		System.out.println("allocateDirect="+(System.currentTimeMillis()-now));
	}
	
	public void map() throws Exception
	{
		RandomAccessFile raf = new RandomAccessFile("e:\\logs\\test.txt", "rw");
        FileChannel fc = raf.getChannel();

        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);

        raf.close();
	}
	
}
