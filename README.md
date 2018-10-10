[![Build Status](https://travis-ci.com/mtumilowicz/spring-data-specification.svg?token=PwyvjePQ7aiAX51hSYLE&branch=master)](https://travis-ci.com/mtumilowicz/spring-data-specification)

# spring-data-specification
The main goal of this project is to show how to use Spring Data 
Specification.

_Reference_: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications

# preview
JPA 2 introduces a criteria API that you can use to build queries 
programmatically. By writing a criteria, you define the where 
clause of a query for a domain class. Taking another step back, 
these criteria can be regarded as a predicate over the entity that 
is described by the JPA criteria API constraints.

For Criteria API please refer my other project: https://github.com/mtumilowicz/jpa-criteria-api

# overview
1. To support specifications, you can extend your repository interface 
with the `JpaSpecificationExecutor` interface, as follows:
    ```
    @Repository
    public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    
    }
    ```
    
    The additional interface has methods that let you execute specifications 
    in a variety of ways:
    ```
    public interface JpaSpecificationExecutor<T> {
        Optional<T> findOne(@Nullable Specification<T> var1);
    
        List<T> findAll(@Nullable Specification<T> var1);
    
        Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
    
        List<T> findAll(@Nullable Specification<T> var1, Sort var2);
    
        long count(@Nullable Specification<T> var1);
    }
    ```
1. The Specification interface is defined as follows:
    ```
    public interface Specification<T> {
      Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                CriteriaBuilder builder);
    }
    ```
    so exemplary implementation (using Java 8):
    ```
    public final class EmployeeSpecifications {
        public static Specification<Employee> hasName(@NonNull String name) {
            return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                    -> cb.equal(root.get(Employee_.name), name);
        }
    
        public static Specification<Employee> hasAge(int age) {
            return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                    -> cb.equal(root.get(Employee_.age), age);
        }
    }
    ```
1. The power of specifications really shines when you combine them to 
create new Specification objects:
    * or
    ```
    @Test
    public void or() {
        List<Employee> employees = repository.findAll(hasName("Tumilowicz").or(hasName("Mrozek")));

        assertThat(employees, hasSize(2));
    }    
    ```
    * and
    ```
    @Test
    public void and() {
        List<Employee> employees = repository.findAll(hasName("Tumilowicz").and(hasAge(29)));

        assertThat(employees, hasSize(1));
    }    
    ```