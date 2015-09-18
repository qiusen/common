package com.dihaiboyun.common.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合算法
 * @author qiusen
 *
 */
public class Combination {
	public static void getCombination(List<List<Integer>> all) {

		
		int[] indexs = new int[all.size()];
		int[] sizes = new int[all.size()];
		for (int i = 0; i < all.size(); i++) {
			List<Integer> list = all.get(i);

			indexs[i] = 0;
			sizes[i] = list.size() - 1;
			System.out.print(sizes[i] + " ");
		}
		System.out.println();
		
		int i = 0;
		while (true) {
			boolean b = true;

			indexs[indexs.length - 1] = i;

			for (int j = 0; j < indexs.length; j++) {
//				System.out.println(indexs[j]);
//				System.out.println(sizes[j]);
				if (indexs[j] != sizes[j]) {
					b = false;
					break;
				}
			}
			if (b) {
				for (int x = 0; x < all.size(); x++) {
					List<Integer> list = all.get(x);
					System.out.print("[" + list.get(indexs[x])+ "]");

				}
				System.out.println();

				System.out
						.println("================遍历结束=================");
				break;
			}
			
			for (int z = indexs.length - 1; z >= 1; z--) {
				if (indexs[z] == sizes[z] + 1) {
					indexs[z - 1] = indexs[z - 1] + 1;
					indexs[z] = 0;
					i = 0;
				}
			}
			
			for (int x = 0; x < all.size(); x++) {
				List<Integer> list = all.get(x);
				System.out.print("[" + list.get(indexs[x])+ "]");
			}
			System.out.println();

			i++;
		}
	}

	public static void main(String[] args) {
		/*
		 * 对下列数组进行组合
		 * 1、2、3、4、5
		 * 6、7、8、9、10
		 * 11、12、13、14、15
		 * 16、17、18、19、20
		 * 21、22、23、24、25
		 * 
		 */
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(6);
		list2.add(7);
		list2.add(8);
		list2.add(9);
		list2.add(10);
		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(11);
		list3.add(12);
		list3.add(13);
		list3.add(14);
		list3.add(15);
		List<Integer> list4 = new ArrayList<Integer>();
		list4.add(16);
		list4.add(17);
		list4.add(18);
		list4.add(19);
		list4.add(20);
		List<Integer> list5 = new ArrayList<Integer>();
		list5.add(21);
		list5.add(22);
		list5.add(23);
		list5.add(24);
		list5.add(25);
		List<List<Integer>> all = new ArrayList<List<Integer>>();
		all.add(list1);
		all.add(list2);
		all.add(list3);
		all.add(list4);
		all.add(list5);
		getCombination(all);
	}
}
