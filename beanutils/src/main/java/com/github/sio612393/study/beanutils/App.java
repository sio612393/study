package com.github.sio612393.study.beanutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Hello world!
 */
public class App {
	private String[][] inputData = {
			{"firstName","lastName","email","postal","プロパティと全く同じ"},
			{"firstname","lastname","email","postal","全部小文字"},
			{"Firstname","Lastname","Email","Postal","先頭一文字だけ大文字"},
			{"FirstName","LastName","Email","Postal","ワード先頭一文字だけ大文字"},
			{"FIRSTNAME","LASTNAME","EMAIL","POSTAL","全部大文字"},
			{"FIrstname","LAstname","EMail","POstal","先頭二文字だけ大文字"},
	};
	

	private App() throws IllegalAccessException, InvocationTargetException {
		
		for(String[] items: inputData) {
			Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put(items[0], "FN");
			inputMap.put(items[1], "LN");
			inputMap.put(items[2], "xxxx@example.com");	// OK
			inputMap.put(items[3], "123-4567");

			OutputBean outputBean = new OutputBean();
			BeanUtils.populate(outputBean, inputMap);

			StringBuilder sb = new StringBuilder();
			sb.append(items[4]).append(": ");
			sb.append(items[0]).append("=").append(outputBean.getFirstName());
			sb.append(",").append(items[1]).append("=").append(outputBean.getLastName());
			sb.append(",").append(items[2]).append("=").append(outputBean.getEmail());
			System.out.println(sb.toString());
		}
		
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		for(String[] items: inputData) {
			Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put(items[0], "FN");
			inputMap.put(items[1], "LN");
			inputMap.put(items[2], "xxxx@example.com");	// OK
			inputMap.put(items[3], "123-4567");

			Map<String, Class> fieldMap = new HashMap<String, Class>();
			Field[] fields = OutputBean.class.getDeclaredFields();
			for(Field field: fields) {
				fieldMap.put(field.getName(), field.getType());
				// TODO need check of type
			}
			
			Map<String, String> adjustedMap = new HashMap<String, String>();
			for(String key: inputMap.keySet()) {
				for(String property: fieldMap.keySet()) {
					// Beanのkeyをcase insensitiveでチェック
					// System.out.println("..." + key + ", " + property);
					if(! property.equalsIgnoreCase(key) ) {
						continue;
					}
					// use right key for Map
					adjustedMap.put(property, inputMap.get(key));
					break;
				}
			}
			
			OutputBean outputBean = new OutputBean();
//			BeanUtils.populate(outputBean, inputMap);
			BeanUtils.populate(outputBean, adjustedMap);
			
//			System.out.println(items[4] + ": "  + outputBean.toString());
			StringBuilder sb = new StringBuilder();
			sb.append(items[4]).append(": ");
			sb.append(items[0]).append("=").append(outputBean.getFirstName());
			sb.append(",").append(items[1]).append("=").append(outputBean.getLastName());
			sb.append(",").append(items[2]).append("=").append(outputBean.getEmail());
			System.out.println(sb.toString());
		}
		// throw new RuntimeException("msg");
//		Map<String, String> inputMap = new HashMap<String, String>();
//		inputMap.put("firstName", "miguel");
//		inputMap.put("LastName", "rossi");
//		inputMap.put("email", "xxxx@example.com");	// OK
//		inputMap.put("Postal", "123-4567");
//		
//		OutputBean outputBean = new OutputBean();
//		BeanUtils.populate(outputBean, inputMap);
//		
//		System.out.println(outputBean.toString());
		return;
	}

	public static void main(String[] args) {
		try {
			new App();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public class OutputBean {
		private String firstName;
		private String lastName;
		private String email;

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
//			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
//			return String.format(
//					"OutputBean [fiestName=%s, lastName=%s, email=%s, getFiestName()=%s, getLastName()=%s, getEmail()=%s]",
//					fiestName, lastName, email, getFiestName(), getLastName(), getEmail());
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

}
