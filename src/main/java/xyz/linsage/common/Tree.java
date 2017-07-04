package xyz.linsage.common;

import com.google.common.base.Strings;

import java.util.*;


/**
 * @author linsage
 * @create 2017-06-28  下午2:44
 */
public class Tree {
	// NB: LinkedHashSet preserves insertion order
	private final Set<Tree> children = new LinkedHashSet<Tree>();
	private final String name;      //名称（类或属性名）
	private String type;            //属性数据类型
	private int level;          //层级（0-开始）

	Tree(String name, String type, int level) {
		this.name = name;
		this.type = Strings.isNullOrEmpty(type) ? "String" : type;
		this.level = level;
	}

	Tree child(String name, String type, int level) {
		for (Tree child : children) {
			if (child.name.equals(name)) {
				return child;
			}
		}
		return child(new Tree(name, type, level));
	}

	Tree child(Tree child) {
		children.add(child);
		return child;
	}


	public boolean isClass() {
		return children.size() != 0;
	}

	public Set<Tree> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getLevel() {
		return level;
	}


	/**
	 * 建立树
	 *
	 * @param properties
	 * @return
	 */
	public static Tree build(Properties properties) {
		List<String> data = new ArrayList(properties.stringPropertyNames());
		Tree current = new Tree("root", null, 0);

		for (String path : data) {
			//跳过类型信息
			if (path.endsWith(".type"))
				continue;
			Tree root = current;
			//取对应数据类型
			String type = properties.getProperty(path + ".type");

			String[] child = path.split("\\.");

			for (String name : child) {
				current = current.child(name, type, child.length - 1);
			}
			current = root;
		}

		return current;
	}


}
