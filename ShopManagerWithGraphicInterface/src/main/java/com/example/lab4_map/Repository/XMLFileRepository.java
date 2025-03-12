package com.example.lab4_map.Repository;//package demo.Repository;
//
//import demo.Domain.Entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class XMLRepository<T extends Entity<T>> extends FileRepository<T> {
//
//    private final Class<T> type;
//
//    public XMLRepository(String fileName, Class<T> type) {
//        super(fileName);
//        this.type = type;
//    }
//
//    @Override
//    protected void writeToFile() {
//        try {
//            JAXBContext context = JAXBContext.newInstance(type);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(new ArrayList<>(items), file);
//        } catch (JAXBException e) {
//            throw new RuntimeException("Error writing to XML file", e);
//        }
//    }
//
//    @Override
//    protected void loadFromFile() {
//        if (!file.exists()) return;
//
//        try {
//            JAXBContext context = JAXBContext.newInstance(type);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            items.clear();
//            items.addAll((List<T>) unmarshaller.unmarshal(file));
//        } catch (JAXBException e) {
//            throw new RuntimeException("Error loading from XML file", e);
//        }
//    }
//}
//
