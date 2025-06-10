create database plantsproducts;

use plantsproducts;

create table product(
	product_id bigint primary key auto_increment,
    name varchar(50) unique not null,
    description varchar(500),
    image varchar(200),
    price decimal(7,2) not null,
    stock_quantity int
);

create table category(
	category_id bigint primary key auto_increment,
    category varchar (500) not null
);

create table product_category(
	category_id bigint,
    product_id bigint,
    primary key(product_id,category_id),
    foreign key (product_id) references product(product_id),
    foreign key (category_id) references category(category_id)
);

INSERT INTO category (category) VALUES 
('Air Purifying Plants'),
('Aromatic Fragrant Plants'),
('Insect Repellent Plants'),
('Medicinal Plants'),
('Low Maintenance Plants');

-- Insert products (Air Purifying Plants)
INSERT INTO product (name, description, image, price, stock_quantity) VALUES 
('Snake Plant', 'Produces oxygen at night, improving air quality.', 'https://cdn.pixabay.com/photo/2021/01/22/06/04/snake-plant-5939187_1280.jpg', 15.00, 50),
('Spider Plant', 'Filters formaldehyde and xylene from the air.', 'https://cdn.pixabay.com/photo/2018/07/11/06/47/chlorophytum-3530413_1280.jpg', 12.00, 50),
('Peace Lily', 'Removes mold spores and purifies the air.', 'https://cdn.pixabay.com/photo/2019/06/12/14/14/peace-lilies-4269365_1280.jpg', 18.00, 50),
('Boston Fern', 'Adds humidity to the air and removes toxins.', 'https://cdn.pixabay.com/photo/2020/04/30/19/52/boston-fern-5114414_1280.jpg', 20.00, 50),
('Rubber Plant', 'Easy to care for and effective at removing toxins.', 'https://cdn.pixabay.com/photo/2020/02/15/11/49/flower-4850729_1280.jpg', 17.00, 50),
('Aloe Vera', 'Purifies the air and has healing properties for skin.', 'https://cdn.pixabay.com/photo/2018/04/02/07/42/leaf-3283175_1280.jpg', 14.00, 50);

-- Insert products (Aromatic Fragrant Plants)
INSERT INTO product (name, description, image, price, stock_quantity) VALUES 
('Lavender', 'Calming scent, used in aromatherapy.', 'https://images.unsplash.com/photo-1611909023032-2d6b3134ecba?q=80&w=1074&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 20.00, 50),
('Jasmine', 'Sweet fragrance, promotes relaxation.', 'https://images.unsplash.com/photo-1592729645009-b96d1e63d14b?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 18.00, 50),
('Rosemary', 'Invigorating scent, often used in cooking.', 'https://cdn.pixabay.com/photo/2019/10/11/07/12/rosemary-4541241_1280.jpg', 15.00, 50),
('Mint', 'Refreshing aroma, used in teas and cooking.', 'https://cdn.pixabay.com/photo/2016/01/07/18/16/mint-1126282_1280.jpg', 12.00, 50),
('Lemon Balm', 'Citrusy scent, relieves stress and promotes sleep.', 'https://cdn.pixabay.com/photo/2019/09/16/07/41/balm-4480134_1280.jpg', 14.00, 50),
('Hyacinth', 'Hyacinth is a beautiful flowering plant known for its fragrant.', 'https://cdn.pixabay.com/photo/2019/04/07/20/20/hyacinth-4110726_1280.jpg', 22.00, 50);

-- Insert products (Insect Repellent Plants)
INSERT INTO product (name, description, image, price, stock_quantity) VALUES 
('Oregano', 'The oregano plants contains compounds that can deter certain insects.', 'https://cdn.pixabay.com/photo/2015/05/30/21/20/oregano-790702_1280.jpg', 10.00, 50),
('Marigold', 'Natural insect repellent, also adds color to the garden.', 'https://cdn.pixabay.com/photo/2022/02/22/05/45/marigold-7028063_1280.jpg', 8.00, 50),
('Geraniums', 'Known for their insect-repelling properties while adding a pleasant scent.', 'https://cdn.pixabay.com/photo/2012/04/26/21/51/flowerpot-43270_1280.jpg', 20.00, 50),
('Basil', 'Repels flies and mosquitoes, also used in cooking.', 'https://cdn.pixabay.com/photo/2016/07/24/20/48/tulsi-1539181_1280.jpg', 9.00, 50),
('Catnip', 'Repels mosquitoes and attracts cats.', 'https://cdn.pixabay.com/photo/2015/07/02/21/55/cat-829681_1280.jpg', 13.00, 50);

-- Insert products (Medicinal Plants)
INSERT INTO product (name, description, image, price, stock_quantity) VALUES 
('Echinacea', 'Boosts immune system, helps fight colds.', 'https://cdn.pixabay.com/photo/2014/12/05/03/53/echinacea-557477_1280.jpg', 16.00, 50),
('Peppermint', 'Relieves digestive issues and headaches.', 'https://cdn.pixabay.com/photo/2017/07/12/12/23/peppermint-2496773_1280.jpg', 13.00, 50),
('Chamomile', 'Soothes anxiety and promotes sleep.', 'https://cdn.pixabay.com/photo/2016/08/19/19/48/flowers-1606041_1280.jpg', 15.00, 50),
('Calendula', 'Heals wounds and soothes skin irritations.', 'https://cdn.pixabay.com/photo/2019/07/15/18/28/flowers-4340127_1280.jpg', 12.00, 50);

-- Insert products (Low Maintenance Plants)
INSERT INTO product (name, description, image, price, stock_quantity) VALUES 
('ZZ Plant', 'Thrives in low light and requires minimal watering.', 'https://images.unsplash.com/photo-1632207691143-643e2a9a9361?q=80&w=464&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 25.00, 50),
('Pothos', 'Tolerates neglect and can grow in various conditions.', 'https://cdn.pixabay.com/photo/2018/11/15/10/32/plants-3816945_1280.jpg', 10.00, 50),
('Cast Iron Plant', 'Hardy plant that tolerates low light and neglect.', 'https://cdn.pixabay.com/photo/2017/02/16/18/04/cast-iron-plant-2072008_1280.jpg', 20.00, 50),
('Succulents', 'Drought-tolerant plants with unique shapes and colors.', 'https://cdn.pixabay.com/photo/2016/11/21/16/05/cacti-1846147_1280.jpg', 18.00, 50),
('Aglaonema', 'Requires minimal care and adds color to indoor spaces.', 'https://cdn.pixabay.com/photo/2014/10/10/04/27/aglaonema-482915_1280.jpg', 22.00, 50);

-- Air Purifying Plants (category_id = 1)
INSERT INTO product_category (product_id, category_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1);

-- Aromatic Fragrant Plants (category_id = 2)
INSERT INTO product_category (product_id, category_id) VALUES
(7, 2), (8, 2), (9, 2), (10, 2), (11, 2), (12, 2);

-- Insect Repellent Plants (category_id = 3)
INSERT INTO product_category (product_id, category_id) VALUES
(13, 3), (14, 3), (15, 3), (16, 3), (7, 3), (17, 3);

-- Medicinal Plants (category_id = 4)
INSERT INTO product_category (product_id, category_id) VALUES
(6, 4), (18, 4), (19, 4), (11, 4), (20, 4), (21, 4);

-- Low Maintenance Plants (category_id = 5)
INSERT INTO product_category (product_id, category_id) VALUES
(22, 5), (23, 5), (1, 5), (24, 5), (25, 5), (26, 5);


select category.*, product.*
from product inner join product_category on product.product_id = product_category.product_id inner join category on category.category_id = product_category.category_id
order by category.category_id;
