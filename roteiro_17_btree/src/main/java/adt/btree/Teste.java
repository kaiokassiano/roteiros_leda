//package adt.btree;
//
//import adt.btree.BTree;
//import adt.btree.BTreeImpl;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//
//public class Teste{
//
//    BTree<Integer> bt;
//
//    @Before
//    public void setUp(){
//        bt = new BTreeImpl<>(3);
//    }
//
//    @Test
//    public void testInsert(){
//        bt.insert(1);
//        bt.insert(2);
//        assertEquals(1, bt.height());
//        bt.insert(3);
//        assertEquals(2, bt.height());
//        assertArrayEquals(bt.getRoot().getElements().toArray(), new Integer[] {2});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getElements().toArray(), new Integer[] {1});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getElements().toArray(), new Integer[] {3});
//
//
//
//        bt.insert(4);
//        assertArrayEquals(bt.getRoot().getElements().toArray(), new Integer[] {2});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getElements().toArray(), new Integer[] {1});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getElements().toArray(), new Integer[] {3,4});
//
//        bt.insert(5);
//        assertArrayEquals(bt.getRoot().getElements().toArray(), new Integer[] {2,4});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getElements().toArray(), new Integer[] {1});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getElements().toArray(), new Integer[] {3});
//        assertArrayEquals(bt.getRoot().getChildren().get(2).getElements().toArray(), new Integer[] {5});
//
//        bt.insert(6);
//        assertArrayEquals(bt.getRoot().getElements().toArray(), new Integer[] {2,4});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getElements().toArray(), new Integer[] {1});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getElements().toArray(), new Integer[] {3});
//        assertArrayEquals(bt.getRoot().getChildren().get(2).getElements().toArray(), new Integer[] {5,6});
//
//        bt.insert(7);
//        assertArrayEquals(bt.getRoot().getElements().toArray(), new Integer[] {4});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getElements().toArray(), new Integer[] {2});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getChildren().get(0).getElements().toArray(), new Integer[] {1});
//        assertArrayEquals(bt.getRoot().getChildren().get(0).getChildren().get(1).getElements().toArray(), new Integer[] {3});
//
//
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getElements().toArray(), new Integer[] {6});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getChildren().get(0).getElements().toArray(), new Integer[] {5});
//        assertArrayEquals(bt.getRoot().getChildren().get(1).getChildren().get(1).getElements().toArray(), new Integer[] {7});
//
//
//
//    }
//
//
//    @Test
//	public void testInsert2() {
//		
//		bt.insert(13);
//		bt.insert(9);
//		bt.insert(5);
//		
//		assertEquals(bt.size(), 3);
//		assertEquals(bt.height(), 0);
//		
//		bt.insert(12);
//		bt.insert(99);
//		bt.insert(-1);
//		
//		assertEquals(bt.size(), 6);
//		assertEquals(bt.height(), 1);
//		
//		bt.insert(122);
//		bt.insert(3);
//		
//		assertEquals(bt.size(), 8);
//		assertEquals(bt.height(), 1);
//		
//		bt.insert(76);
//		bt.insert(77);
//		
//		assertEquals(bt.size(), 10);
//		assertEquals(bt.height(), 1);
//		
//		bt.insert(78);
//		bt.insert(79);
//		
//		assertEquals(bt.size(), 12);
//		assertEquals(bt.height(), 2);
//		
//		bt.insert(58);
//		bt.insert(70);
//		
//		assertEquals(bt.size(), 14);
//		assertEquals(bt.height(), 2);
//		
//		bt.insert(59);
//		bt.insert(133);
//		bt.insert(140);
//		
//		assertEquals(bt.size(), 17);
//		assertEquals(bt.height(), 2);
//		
//		bt.insert(141);
//		bt.insert(142);
//		bt.insert(143);
//		bt.insert(144);
//		
//		assertEquals(bt.size(), 21);
//		assertEquals(bt.height(), 2);
//		
//		bt.insert(145);
//		bt.insert(146);
//		bt.insert(147);
//		bt.insert(148);
//		
//		assertEquals(bt.size(), 25);
//		assertEquals(bt.height(), 3);
//	}
//
//}