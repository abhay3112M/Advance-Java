class App{
    public static void main(String[] args){
        
        business.furnitures.Bed bed = new business.furnitures.Bed();
        business.furnitures.Chair chair = new business.furnitures.Chair();
        business.furnitures.Table table1 = new business.furnitures.Table();

        business.services.StoreManager storeManager = new business.services.StoreManager();

        utils.data.List list = new utils.data.List();
        utils.data.QuickSort quickSort = new utils.data.QuickSort();
        utils.data.Table table2 = new utils.data.Table();

        utils.presentation.Input input = new utils.presentation.Input();
        utils.presentation.Printer printer = new utils.presentation.Printer();

        System.out.println(bed);
        System.out.println(chair);
        System.out.println(table1.getPrice());

        System.out.println(storeManager);

        System.out.println(list);
        System.out.println(quickSort);
        System.out.println(table2.getColumnCount());

        System.out.println(input);
        System.out.println(printer);
    }
}