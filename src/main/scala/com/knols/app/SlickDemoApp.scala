package com.knols.app

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.MappedTypeMapper
import java.sql.Date

object SlickDemoApp extends App {

  val dbObject = Database.forURL("jdbc:postgresql://localhost:5432/slickdemo", "sky", "satendra",
    null, "org.postgresql.Driver")

  case class Emp(id: Int, name: String, email: String, designation: String, doj: Date)

  object Employees extends Table[Emp]("emp") {
    def id = column[Int]("id", O.PrimaryKey)
    def name = column[String]("name", O.NotNull, O.DBType("VARCHAR(100)"))
    def email = column[String]("email", O.NotNull, O.DBType("VARCHAR(100)"))
    def designation = column[String]("designation", O.NotNull, O DBType ("VARCHAR(100)"))
    def doj = column[Date]("doj", O.NotNull)
    def * = id ~ name ~ email ~ designation ~ doj <> (Emp.apply _, Emp unapply _)
  }

  case class Project(id: Int, name: String, location: String)

  object Projects extends Table[Project]("project") {
    def id = column[Int]("id", O.PrimaryKey, O.DBType("INT"))
    def name = column[String]("name", O.DBType("VARCHAR(100)"))
    def location = column[String]("location", O.DBType("VARCHAR(100)"))
    def * = id ~ name ~ location <> (Project, Project unapply _)
  }

  case class EmpProject(empId: Int, projectId: Int)

  object EmpProjects extends Table[EmpProject]("emp_project") {
    def empId = column[Int]("empid", O.DBType("INT"))
    def projectId = column[Int]("projectid", O.DBType("INT"))
    def * = empId ~ projectId <> (EmpProject, EmpProject unapply _)
    def empFKey = foreignKey("emp_id_fkey", empId, Employees) { employees => employees.id }
    def projectFKey = foreignKey("project_id_fkey", projectId, Projects) { projects => projects.id }
    def empProjectPKey = primaryKey("emp_project_pkey", (empId, projectId))
  }

  // connecting to database
  dbObject.withSession { implicit session: Session =>

    /*    val listOfEmp = List(
      Emp(1, "Janmejani", "Janmejani@knoldus.com", "consultant", java.sql.Date.valueOf("2012-11-26")),
      Emp(2, "Anand", "anand@knoldus.com", "consultant", java.sql.Date.valueOf("2013-07-01")),
      Emp(3, "Rishi Khandelwal ", "rishi@knoldus.com", "consultant", java.sql.Date.valueOf("2012-08-29")),
      Emp(4, "Neelkanth Sachdeva", "neel@knoldus.com", "seniorconsultant", java.sql.Date.valueOf("2011-07-04")),
      Emp(5, "piyush", "piyush@knoldus.com", "consultant", java.sql.Date.valueOf("2012-05-07")),
      Emp(6, "sanjeev", "sanjeev@knoldus.com", "consultant", java.sql.Date.valueOf("2013-07-10")),
      Emp(7, "Mayank Bairagi", "mayank@knoldus.com", "seniorconsultant", java.sql.Date.valueOf("2012-07-31")),
      Emp(8, "satendra kumar", "satendra@knoldus.com", "consultant", java.sql.Date.valueOf("2013-06-3")))

    Employees.insertAll(listOfEmp: _*)
    
    val listofProject = List(
      Project(1001, "decooda", "US"),
      Project(1002, "recommendo", "Germany"),
      Project(1003, "BeanStream", "US"),
      Project(1004, "tutorme", "UK"),
      Project(1005, "scalageek", "india"),
      Project(1006, "newsfeed", "india"),
      Project(1007, "datacube", "US"))

    Projects.insertAll(listofProject: _*)

    val listOfEmpProject = List(
      EmpProject(1, 1006),
      EmpProject(2, 1001),
      EmpProject(3, 1004),
      EmpProject(4, 1004),
      EmpProject(4, 1003),
      EmpProject(5, 1007),
      EmpProject(6, 1005),
      EmpProject(6, 1002),
      EmpProject(7, 1001),
      EmpProject(8, 1001))
      
    EmpProjects.insertAll(listOfEmpProject: _*) */

     val query = (for {
      emp <- Employees
      empproject <- EmpProjects if emp.id === empproject.empId
      project <- Projects if (empproject.projectId === project.id)
    } yield (emp.name, project.name,project.location))
   
    println(s"""              
        
       *******************************************************convert in sql query************************************************
                                  
        
      ${query.list}                                                    
       																																			 	
                                                                                                                                                    
                                                                                                                                                    
                                                                                                                                                    
       *******************************************************query results*********************************************************
                                            
       ${}
     
    
    """)

  }

}