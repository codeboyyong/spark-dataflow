/*
 * Copyright (c) 2015, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package com.cloudera.dataflow.extension.io;

import java.util.List;

import com.google.cloud.dataflow.sdk.transforms.PTransform;
import com.google.cloud.dataflow.sdk.util.WindowingStrategy;
import com.google.cloud.dataflow.sdk.values.PCollection;
import com.google.cloud.dataflow.sdk.values.PInput;

/**
 * My first try of io interface
 * https://www.mapr.com/developercentral/code/loading
 * -hbase-tables-spark#.VbZazdNVhBc
 * 
 * @author yong_zhao
 *
 */
public final class HBaseIO {

	// properties for hbase
	// public static final String HBASE_ZK_HOST = "hbase.zookeeper.quorum";
	// //127.0.0.1
	// public static final String HBASE_ZK_PORT =
	// "hbase.zookeeper.property.clientPort";//2181
	//
	// public static final String HBASE_TABLE = "hbase.table.name"; //table

	private HBaseIO() {
	}

	public static final class Read {

		private Read() {
		}

		public static Bound from(String hbaseZkConnection,// 127.0.0.1:2181
				String tableName, List<String> columnNames) {
			return new Bound(hbaseZkConnection,// 127.0.0.1:2181
					tableName, columnNames);
		}

		public static class Bound extends
				PTransform<PInput, PCollection<String[]>> {

			@Override
			public PCollection<String[]> apply(PInput input) {
				return PCollection.createPrimitiveOutputInternal(
						input.getPipeline(), WindowingStrategy.globalDefault(),
						PCollection.IsBounded.BOUNDED);
			}

			/**
			 * 
			 */
			private static final long serialVersionUID = 146371018435117952L;
			private String zkConnection;

			public String getZkConnection() {
				return zkConnection;
			}

			public void setZkConnection(String zkConnection) {
				this.zkConnection = zkConnection;
			}

			public String getTableName() {
				return tableName;
			}

			public void setTableName(String tableName) {
				this.tableName = tableName;
			}

			public List<String> getColumnNames() {
				return columnNames;
			}

			public void setColumnNames(List<String> columnNames) {
				this.columnNames = columnNames;
			}

			private String tableName;
			private List<String> columnNames;

			public Bound(String zkConnection, String tableName,
					List<String> columnNames) {
				this.zkConnection = zkConnection;
				this.tableName = tableName;
				this.columnNames = columnNames;
			}

		}

	}
}
