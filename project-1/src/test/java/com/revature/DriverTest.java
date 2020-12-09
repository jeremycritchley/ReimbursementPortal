package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.ReimStatus;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.LoginServiceImpl;
import com.revature.services.ManagerServiceImpl;

public class DriverTest {
	
	@InjectMocks
	private EmployeeServiceImpl empServ;
	
	@InjectMocks
	private ManagerServiceImpl manServ;
	
	@InjectMocks
	private LoginServiceImpl ls;
	
	@Mock
	private UserDAO userd;
	
	@Mock
	private ReimDAO reimd;
	
	private User u;
	

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
		u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		userd = mock(UserDAO.class);
		reimd = mock(ReimDAO.class);
		ls = new LoginServiceImpl(userd);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateInfo() {
		empServ = new EmployeeServiceImpl(userd);
		UserDTO udto = Mockito.mock(UserDTO.class);
		
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.update(u)).thenReturn(true);
		
		assertEquals(empServ.updateInfo(udto), true);
	}
	
	@Test
	public void testUpdateInfoFailure() {
		empServ = new EmployeeServiceImpl(userd);
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(-11, null, "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.update(u)).thenReturn(false);
		
		assertEquals(empServ.updateInfo(udto), false);
	}
	
	@Test
	public void testSubmitReim() {
		empServ = new EmployeeServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", u, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		when(rdto.getType()).thenReturn("lodge");
		
		when(reimd.insert(reim)).thenReturn(1);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(empServ.submitReim(rdto, "1"), 1);
	}
	
	@Test
	public void testSubmitReimFailAmount() {
		empServ = new EmployeeServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(-500.00, "23/11/2020 14:36:10", null, "description", u, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.insert(reim)).thenReturn(0);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(empServ.submitReim(rdto, "1"), 0);
	}
	
	@Test
	public void testSubmitReimFailNullAuthor() {
		empServ = new EmployeeServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", null, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.insert(reim)).thenReturn(1);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(empServ.submitReim(rdto, "0"), 0);
	}
	
	@Test
	public void testViewReimByStatus() {
		empServ = new EmployeeServiceImpl(reimd);
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", u, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.selectAll("Author_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(empServ.viewRiemsByStatus("1", false), null);
		
	}
	
	@Test
	public void testViewReimByStatusUserDoesNotExist() {
		empServ = new EmployeeServiceImpl(reimd);
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", u, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.selectAll("Author_FK", "1")).thenReturn(null);
		
		assertEquals(empServ.viewRiemsByStatus("1", false), null);
		
	}
	
	
	@Test
	public void testApproveReim() {
		manServ = new ManagerServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", u, u, new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		when(rdto.getType()).thenReturn("lodge");
		
		when(reimd.update(reim)).thenReturn(true);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(manServ.approveReim(rdto, "1"), true);
	}
	
	@Test
	public void testApproveReimFail() {
		manServ = new ManagerServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", u, u, new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		when(rdto.getType()).thenReturn("lodge");
		
		when(reimd.update(reim)).thenReturn(false);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(manServ.approveReim(rdto, "0"), false);
	}
	
	@Test
	public void testDenyReim() {
		manServ = new ManagerServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", u, u, new ReimStatus(3), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		when(rdto.getType()).thenReturn("lodge");
		
		when(reimd.update(reim)).thenReturn(true);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(manServ.denyReim(rdto, "1"), true);
	}
	
	@Test
	public void testDenyReimFail() {
		manServ = new ManagerServiceImpl(userd, reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", u, u, new ReimStatus(3), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		when(rdto.getType()).thenReturn("lodge");
		
		when(reimd.update(reim)).thenReturn(false);
		when(userd.selectById(1)).thenReturn(u);
		
		assertEquals(manServ.denyReim(rdto, "0"), false);
	}
	
	@Test
	public void testViewAllEmployees() {
		manServ = new ManagerServiceImpl(userd);
		
		when(userd.selectAll("Role_FK", "2")).thenReturn(new ArrayList<User>());
		assertNotEquals(manServ.viewAllEmployees(), null);
	}
	
	@Test
	public void testViewReimsByStatusPending() {
		manServ = new ManagerServiceImpl(reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.selectAll("Status_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(manServ.viewRiemsByStatus("1", false), null);
		
	}
	
	@Test
	public void testViewReimsByStatusResolved() {
		manServ = new ManagerServiceImpl(reimd);
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance(userd)).thenReturn(reim);
		when(rdto.getReimInstance(reimd)).thenReturn(reim);
		
		when(reimd.selectAll("Status_FK!", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(manServ.viewRiemsByStatus("1", true), null);
		
	}
	
	@Test
	public void testViewByUser() {
		empServ = new EmployeeServiceImpl(userd);
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.selectById(1)).thenReturn(u);
		
		assertNotEquals(empServ.viewByUser("1"), null);
	}
	
	@Test
	public void testViewByUserFail() {
		manServ = new ManagerServiceImpl(userd);
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.selectById(1)).thenReturn(null);
		
		assertEquals(manServ.viewByUser("1"), null);
	}
	
	@Test
	public void testLogin() {
		//UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		//when(udto.createUserInstance()).thenReturn(u);
		//when(udto.getUserInstance()).thenReturn(u);
		UserDTO ud = new UserDTO(u);
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertEquals(ls.login("username", "password").getUserId(), ud.getUserId());
		
		
	}
	
	@Test
	public void testLoginInvalidPassword() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertEquals(ls.login("username", "p"), null);
	}
	
	@Test
	public void testLoginInvalidUsername() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance(userd)).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertEquals(ls.login("u", "password"), null);
		
		
	}
	
	@Test
	public void testViewReimsByEmployee() {
		empServ = new EmployeeServiceImpl(reimd);
		when(reimd.selectAll("Author_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		assertNotEquals(empServ.viewReimsByEmployee("1"), null);
	}
	
	@Test
	public void testViewReimsByEmployeeFail() {
		manServ = new ManagerServiceImpl(reimd);
		when(reimd.selectAll("Author_FK", "1")).thenReturn(null);
		assertEquals(manServ.viewReimsByEmployee("1"), null);
	}

}
