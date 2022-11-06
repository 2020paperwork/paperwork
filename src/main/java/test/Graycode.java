package test;

import java.util.Arrays;

public class Graycode
{
    private double length; //每个单位格子的长度
    //private int order; //格雷码的阶数
    private String[] Model;

    public Graycode(int order,double length) //设置构造函数必须制定格雷码的阶数和单位格子的长度
    {
        this.Model=init(order);
        this.length=length;
    }

    private int judge(int[] data_1,int[] data_2)
    {
        int len=data_1.length,count=0;
        int ret=-1;
        for (int i=0;i<len;i++)
        {
            if (data_1[i]!=data_2[i])
            {
                ret=i;
                count=count+1;
            }

            if (count>1)
                break;
        }
        if (count!=1)
        {
            ret=-1;
        }
        return ret;
    }

    public int[][] syntheic(int [][] data)//合并函数
    {
        int len_1=data.length,len_2=data[0].length,place,flag=0;
        int[][] ret=new int[data.length][data[0].length];
        for (int i = 0; i <data.length; i++)
        {
            ret[i] = Arrays.copyOf(data[i], data[i].length);
        }
        int[] temp;

        for (int i=0;i<len_1;i++)
        {
            for (int j=0;j<len_1;j++)
            {
                place=judge(data[i],data[j]);
                if (place!=-1)
                {
                    flag=1;
                    temp=Arrays.copyOf(data[j], data[j].length);
                    temp[place]=-1;
                    ret[j]=temp;//将合并后结果存入
                }
            }
        }
        if (flag == 1)
        {
            ret=syntheic(ret);//递归重复调用
        }
        if (flag==0)//在最后一轮递归去掉空的元素
        {
            int count=ret.length;
            int[] index = new int[ret.length];
            for(int i=0;i<ret.length;i++)//count来计算不为空的行数
            {
                if (index[i] == 1)
                {
                    continue;
                }
                for(int j = i + 1; j < ret.length; j++)
                {
                    if (compareInt(ret[i], ret[j]) == true)
                    {
                        count--;
                        index[j] = 1;
                    }
                }
            }

            int[][] temp_2=new int[count][len_2];
            int k=0;//下标
            for(int i=0;i<len_1;i++)
            {
                if (index[i] == 0)
                {
                    temp_2[k]=ret[i];
                    k=k+1;
                }
            }
            ret=temp_2;
        }
        return ret;
    }
    public static boolean compareInt(int[] a, int[] b)
    {
        for (int i = 0; i < a.length; i++)
        {
            if (a[i] != b[i])
            {
                return false;
            }
        }
        return true;
    }
    public void setorder(int order)//后续可以调节格雷码的阶数
    {
        this.Model=init(order);;//设置格雷码的阶数
    }

    public void setLength(double length)//后续可以修改每个单位格子的长度
    {
        this.length=length;
    }
    private String[] init(int n) {

        String[] graycode = new String[(int) Math.pow(2, n)];
        if(n == 1){
            graycode[0] = "0";
            graycode[1] = "1";
            return graycode;
        }

        String[] last=init(n - 1);

        for(int i = 0;i < last.length;i++){
            graycode[i] = "0" + last[i];
            graycode[graycode.length - i - 1] = "1" + last[i];

        }
//        System.out.println(Arrays.toString(graycode));
        return graycode;
    }   //以字符串的形式储存所有的n阶格雷码 后续进行转换时更高效

    public int[] transform(double x,double y) //返回的格式是 y的格雷码+x的格雷码
    {
        int temp_x=(int)(x/length),temp_y=(int)(y/length);

        String temp=Model[temp_y];
        int len=temp.length();
        int[] ret=new int[len*2];
        for(int i=0;i<len;i++)
        {
            ret[i]=(int)(temp.charAt(i))-48;
        }
        temp=Model[temp_x];
        for(int i=len;i<len*2;i++)
        {
            ret[i]=(int)(temp.charAt(i-len))-48;
        }
        return ret;
    }

    public String arrayToString(int[] a, int begin, int end)
    {
        String ret = "";
        for (int i = begin; i < end; i++)
        {
            ret = ret + a[i];
        }
        return ret;
    }

    public int[] stringToArray(String s)
    {
        int[] ret = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
        {
            ret[i] = (int)(s.charAt(i)) - 48;
        }
        return ret;
    }

    public int min(int[] a)
    {
        int m = 0;
        for (int i : a)
        {
            if (i < m)
            {
                m = i;
            }
        }
        return m;
    }

    public int max(int[] a)
    {
        int m = 0;
        for (int i : a)
        {
            if (i > m)
            {
                m = i;
            }
        }
        return m;
    }

    // 输入矩形4个端点的格雷码，返回含有通配符的tk
    public int[][] fourPoint(int[][] p)
    {
        int[] xIndex = new int[4];
        int[] yIndex = new int[4];
        for (int i = 0; i < xIndex.length; i++)
        {
            String xTmp = arrayToString(p[i], p[i].length / 2, p[i].length);
            String yTmp = arrayToString(p[i], 0, p[i].length / 2);
            for (int j = 0; j < Model.length; j++)
            {
                if (Model[j].equals(xTmp))
                {
                    xIndex[i] = j;
                }
                if (Model[j].equals(yTmp))
                {
                    yIndex[i] = j;
                }
            }
        }
        int[] x = new int[2];
        int[] y = new int[2];
        x[0] = min(xIndex);
        x[1] = max(xIndex);
        y[0] = min(yIndex);
        y[1] = max(yIndex);

        int[][] tk = new int[(y[1] - y[0] + 1) * (x[1] - x[0] + 1)][];
        int k = 0;
        for (int i = x[0]; i <= x[1]; i++)
        {
            for (int j = y[0]; j <= y[1]; j++)
            {
                String tmp = Model[j] + Model[i];
                tk[k++] = stringToArray(tmp);
            }
        }
        return syntheic(tk);
    }

    public static void main(String[] arguments)
    {
        Graycode test=new Graycode(2,1);
        int[][] p = {{0,0,0,0}, {0,0,1,0}, {1,0,1,0}, {0,1,0,0}};
        int[][] t = test.fourPoint(p);
        System.out.println(Arrays.deepToString(t));

        int[][] p2 = {{0,0,0,0}, {0,0,1,1}, {0,1,1,1}, {1,0,0,0}};
        int[][] t2 = test.fourPoint(p2);
        System.out.println(Arrays.deepToString(t2));

        int[][] p3 = {{0,0,0,0}, {0,0,1,1}, {1,1,1,1}, {1,1,0,0}};
        int[][] t3 = test.fourPoint(p3);
        System.out.println(Arrays.deepToString(t3));

    }
}
