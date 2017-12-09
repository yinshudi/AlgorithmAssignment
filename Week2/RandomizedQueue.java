public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[])new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }

        if (n == items.length) {
            resize(n * 2);
        }
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (n <= items.length / 4) {
            resize(n / 2);
        }
        int random = StdRandom.uniform(n);
        //取随机数, 然后将尾部的数字放到随机数处补位,避免数组中有空值
        Item item = items[random];
        items[random] = items[--n];
        items[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(n);
        return items[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator<Item>();
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    //每个迭代器都新建一个大小为 n 的数组保存下标, 然后将下标打乱
    private class RandomIterator<Item> implements Iterator {

        private int[] indices;
        private int cur; //indices当前下标

        public void RandomIterator(){
            indices = new int[n];
            for (int i = 0; i < n; i++) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return indices.length != 0 && cur < indices.length;
        }

        public Item next() {
            if (isEmpty() || !hasNext()) {
                throw new NoSuchElementException();
            }
            int index = indices[cur++];
            return items[index];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}