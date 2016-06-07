<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablebottom" id="nameTable" >
				<tr>
				<th class="bluebgheadtd"></th>
				<th class="bluebgheadtd">Property Tax</th>
			    <th class="bluebgheadtd">Education Cess</th>
			    <th class="bluebgheadtd">Library Cess</th>
			    <th class="bluebgheadtd">Unauthorized Penalty</th>
				<th class="bluebgheadtd">Total Tax</th>
				<th class="bluebgheadtd">Total Tax Due</th>
				</tr>
				<tr>
					  <td class="blueborderfortd" align="center">
						<span class="bold"><s:property value="viewMap.firstHalf" /></span>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfGT" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfEC" />
								</s:text>
					  </td>
					  
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfLC" />
								</s:text>
					  </td>
					  
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfUAP" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfTotal" /> 
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.firstHalfTaxDue" />  
								</s:text>
					  </td>
				</tr>
				<tr>
					  
					  <td class="blueborderfortd" align="center">
						<span class="bold"><s:property value="viewMap.secondHalf" /></span>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfGT" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfEC" />
								</s:text>
					  </td>
					  
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfLC" />
								</s:text>
					  </td>
					   <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfUAP" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfTotal" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.secondHalfTaxDue" />
								</s:text>
					  </td>
				 </tr>
				 <tr>
					 
					  <td class="blueborderfortd" align="center">
						<span class="bold"><s:property value="viewMap.arrears" /></span>
					  </td>
					  <td class="blueborderfortd" align="center">
					  </td>
					  <td class="blueborderfortd" align="center">
					  </td>
					  <td class="blueborderfortd" align="center">
					  </td>
					  <td class="blueborderfortd" align="center">
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.arrearTax" />
								</s:text>
					  </td>
					  <td class="blueborderfortd" align="center">
					  Rs.<s:text name="format.money">
									<s:param value="viewMap.totalArrDue" />
								</s:text>
					  </td>
					  
				 </tr>
</table>