package util;

import java.io.Closeable; 
import java.io.IOException;

public class CloseUtils
{
    /**
     * 关闭所有流
     *
     * @param io
     */
    public static void closeAll(Closeable... io)
    {
        for (Closeable temp : io)
        {
            if (temp != null)
            {
                try
                {
                    temp.close();
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                }
            }
        }
    }
}
