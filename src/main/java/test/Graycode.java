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
        int[][] ret=data;
        int[] temp;

        for (int i=0;i<len_1;i++)
        {
            for (int j=0;j<len_1;j++)
            {
                place=judge(data[i],data[j]);
                if (place!=-1)
                {
                    flag=1;
                    temp=data[i];
                    temp[place]=-1;
                    ret[i]=temp;//将合并后结果存入
                    for(int k=0;k<len_2;k++)
                    {
                        ret[j][k]=2;
                    }
                    ret=syntheic(ret);//递归重复调用
                    break;
                }
            }
        }
        if (flag==0)//在最后一轮递归去掉空的元素
        {
            int count=0;
            for(int i=0;i<len_1;i++)//count来计算不为空的行数
            {
                if (ret[i][0]!=2)
                    count++;
            }

            int[][] temp_2=new int[count][len_2];
            int k=0;//下标
            for(int i=0;i<len_1;i++)
            {
                if (ret[i][0]!=2)
                {
                    temp_2[k]=ret[i];
                    k=k+1;
                }
            }
            ret=temp_2;
        }
        return  ret;
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
        //System.out.println(Arrays.toString(graycode));
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

    public static void main(String[] arguments)
    {
        Graycode test=new Graycode(3,1);//设定格雷码为三阶，即由三位数字组成
        int[][] data={{0,0,1},{0,0,-1}};//测试数据 用于测试化简函数

        data=test.syntheic(data);//化简


        for (int i=0;i<data.length;i++)//输出结果看看
        {
            for(int j=0;j<data[i].length;j++)
            {
                System.out.print(data[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
}
