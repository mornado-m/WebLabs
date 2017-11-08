public class myThread implements Runnable{
    Word thisWord;
    public myThread(Word word){
        thisWord = word;
    }

    @Override
    public void run() {
        while (true){
            while (!thisWord.caught)
                thisWord.Move();
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
