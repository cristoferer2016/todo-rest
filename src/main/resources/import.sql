INSERT INTO user_entity (id, email, username, password, role)
VALUES (NEXTVAL('user_entity_seq'), 'pepe@openwebinars.net', 'pepe', '{noop}12345', 'USER');

INSERT INTO user_entity (id, email, username, password, role)
VALUES (NEXTVAL('user_entity_seq'), 'admin@todo.com', 'admin', '{noop}12345', 'ADMIN');

INSERT INTO user_entity (id, email, username, password, role)
VALUES (NEXTVAL('user_entity_seq'), 'gestor@todo.com', 'gestor', '{noop}12345', 'GESTOR');

INSERT INTO category (id, name)
VALUES (NEXTVAL('category_seq'), 'Personal');

INSERT INTO category (id, name)
VALUES (NEXTVAL('category_seq'), 'Estudios');

INSERT INTO category (id, name)
VALUES (NEXTVAL('category_seq'), 'Trabajo');

INSERT INTO tag (id, name)
VALUES (NEXTVAL('tag_seq'), 'urgente');

INSERT INTO tag (id, name)
VALUES (NEXTVAL('tag_seq'), 'importante');

INSERT INTO tag (id, name)
VALUES (NEXTVAL('tag_seq'), 'spring');

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 7, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Comprar alimentos', 'Hacer una lista de compras para el supermercado.', 'MEDIUM', 'PENDING', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 2, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Pagar facturas', 'Pagar la factura de electricidad antes de la fecha límite.', 'HIGH', 'PENDING', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 14, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Estudiar para el examen', 'Revisar los temas de matemáticas y física para el próximo examen.', 'HIGH', 'IN_PROGRESS', 1, 2);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(HOUR, 12, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Reunión con el equipo', 'Preparar los puntos de la reunión semanal con el equipo de trabajo.', 'HIGH', 'PENDING', 1, 3);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 5, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Llamar al doctor', 'Agendar una cita con el médico para el chequeo anual.', 'MEDIUM', 'PENDING', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (true, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 10, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Limpiar la casa', 'Organizar el cuarto, limpiar las ventanas y aspirar la alfombra.', 'LOW', 'COMPLETED', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 20, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Leer un libro', 'Terminar de leer Cien años de soledad.', 'LOW', 'PENDING', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(HOUR, 48, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Actualizar el currículum', 'Agregar la última experiencia laboral y cursos realizados.', 'MEDIUM', 'IN_PROGRESS', 1, 3);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 3, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Comprar regalos', 'Buscar ideas para los regalos de cumpleaños de la familia.', 'MEDIUM', 'PENDING', 1, 1);

INSERT INTO task (completed, created_at, deadline, id, title, description, priority, status, author_id, category_id)
VALUES (false, CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 15, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Planificar vacaciones', 'Investigar destinos turísticos y elaborar un presupuesto para el viaje.', 'LOW', 'PENDING', 1, 1);

INSERT INTO task_tags (task_id, tags_id)
VALUES (1, 1);

INSERT INTO task_tags (task_id, tags_id)
VALUES (2, 2);

INSERT INTO task_tags (task_id, tags_id)
VALUES (3, 3);