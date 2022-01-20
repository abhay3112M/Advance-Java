package in.conceptarchitect.app;
import in.conceptarchitect.business.furnitures.Table;
import in.conceptarchitect.business.furnitures.Chair;
import in.conceptarchitect.business.services.StoreManager;
import in.conceptarchitect.utils.data.List;
import in.conceptarchitect.utils.data.QuickSort;
import in.conceptarchitect.utils.presentation.Input;
import in.conceptarchitect.utils.presentation.Printer;

import com.dataminers.utils.data.BinarySearch;


class App {

    public static void main(String []args){

        StoreManager manager=new StoreManager();
        
        Chair chair =new Chair();
        Table table1=new Table();


        in.conceptarchitect.utils.data.Table table2=new in.conceptarchitect.utils.data.Table();

        List list=new List();
        
        Input input =new Input();
        
        Printer printer=new Printer();

        QuickSort sort=new QuickSort();

        System.out.println(table1);
        System.out.println(table1.getPrice());

        System.out.println(table2);
        System.out.println(table2.getColumnCount());


        System.out.println(manager);
        System.out.println(chair);
        System.out.println(list);
        System.out.println(input);
        System.out.println(printer);
        System.out.println(sort);

        BinarySearch binarySearch = new BinarySearch();
        System.out.println(binarySearch);

    }
    
}
